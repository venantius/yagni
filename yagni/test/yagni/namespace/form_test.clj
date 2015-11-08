(ns yagni.namespace.form-test
  (:require [clojure.test :refer :all]
            [yagni.namespace.form :as form]
            [yagni.sample-ns]))

(deftest get-form-works
  (is (= (form/get-form 'clojure.core/conj)
         '{:form (def conj
                     (fn conj ([coll x] (. clojure.lang.RT (conj coll x)))
                       ([coll x & xs]
                        (if xs (recur (conj coll x) (first xs) (next xs)) (conj coll x)))))
           :source "(def\n ^{:arglists '([coll x] [coll x & xs])\n   :doc \"conj[oin]. Returns a new collection with the xs\n    'added'. (conj nil item) returns (item).  The 'addition' may\n    happen at different 'places' depending on the concrete type.\"\n   :added \"1.0\"\n   :static true}\n conj (fn ^:static conj \n        ([coll x] (. clojure.lang.RT (conj coll x)))\n        ([coll x & xs]\n         (if xs\n           (recur (conj coll x) (first xs) (next xs))\n           (conj coll x)))))"
           :sym clojure.core/conj})))

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
