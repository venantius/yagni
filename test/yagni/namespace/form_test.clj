(ns yagni.namespace.form-test
  (:require [clojure.test :refer :all]
            [yagni.namespace.form :as form]
            [yagni.sample-ns]))

(deftest get-form-works
  (is (= (form/get-form 'yagni.sample-ns/form-for-testing-get)
         '{:source (defn form-for-testing-get
                     [& args]
                     (+ 1 2 3 (first args)))
           :sym yagni.sample-ns/form-for-testing-get})))

(deftest try-to-resolve-works
  (is (= (form/try-to-resolve 'conj)
         #'clojure.core/conj))
  (is (= (form/try-to-resolve 'bananas)
         nil)))

(deftest maybe-inc-works
  (let [graph (atom {'clojure.core/conj #{}
                     'clojure.core/bananas #{}})] 
    (form/maybe-inc graph 'clojure.core/bananas 'conj)
    (is (= @graph {'clojure.core/conj #{}
                   'clojure.core/bananas #{'clojure.core/conj}}))))

(deftest count-vars-works
  (let [graph (atom {'yagni.sample-ns/y #{}
                     'yagni.sample-ns/x #{}})] 
    (form/count-vars graph ['yagni.sample-ns])
    (is (= @graph
           {'yagni.sample-ns/y #{'yagni.sample-ns/x}
            'yagni.sample-ns/x #{}}))))
