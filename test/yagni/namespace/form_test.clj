(ns yagni.namespace.form-test
  (:require [clojure.test :refer :all]
            [yagni.namespace.form :as form]
            [yagni.sample-ns]))

(deftest get-form-works
  (is (= (form/get-form 'clojure.core/conj)
         '{:source (def conj
            (fn conj ([coll x] (. clojure.lang.RT (conj coll x)))
              ([coll x & xs]
               (if xs (recur (conj coll x) (first xs) (next xs)) (conj coll x)))))
           :sym clojure.core/conj})))

(deftest try-to-resolve-works
  (is (= (form/try-to-resolve 'conj)
         #'clojure.core/conj))
  (is (= (form/try-to-resolve 'bananas)
         nil)))

(deftest maybe-inc-works
  (with-redefs [form/graph (atom {'clojure.core/conj #{}
                                  'clojure.core/bananas #{}})] 
    (form/maybe-inc 'clojure.core/bananas 'conj)
    (is (= @form/graph {'clojure.core/conj #{}
                        'clojure.core/bananas #{'clojure.core/conj}}))))

(deftest count-vars-works
  (with-redefs [form/graph (atom {'yagni.sample-ns/y #{}
                                  'yagni.sample-ns/x #{}})] 
    (form/count-vars ['yagni.sample-ns])
    (is (= @form/graph
           {'yagni.sample-ns/y #{'yagni.sample-ns/x}
            'yagni.sample-ns/x #{}}))))
