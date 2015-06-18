(ns yagni.namespace.form-test
  (:require [clojure.test :refer :all]
            [yagni.namespace.form :as form]
            [yagni.sample-ns]
            ))

(deftest get-form-works
  (is (= (form/get-form 'clojure.core/conj)
         '(def conj
            (fn conj ([coll x] (. clojure.lang.RT (conj coll x)))
              ([coll x & xs]
               (if xs (recur (conj coll x) (first xs) (next xs)) (conj coll x))))))))

(deftest try-to-resolve-works
  (is (= (form/try-to-resolve 'conj)
         #'clojure.core/conj))
  (is (= (form/try-to-resolve 'bananas)
         nil)))

(deftest maybe-inc-works
  (with-redefs [form/fn-graph (atom {'clojure.core/conj 0
                                       'clojure.core/bananas 0})] 
    (form/maybe-inc 'conj)
    (form/maybe-inc 'clojure.core/bananas)
    (is (= @form/fn-graph {'clojure.core/conj 1
                             'clojure.core/bananas 0}))))

(deftest count-fns-works
  (with-redefs [form/fn-graph (atom {'yagni.sample-ns/y 0})] 
    (form/count-fns ['yagni.sample-ns])
    (is (= @form/fn-graph
           {'yagni.sample-ns/y 1}))))
