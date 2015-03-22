(ns yagni.namespace-test
  (:require [clojure.test :refer :all]
            [yagni.namespace :as namesp]
            [yagni.sample-ns :as sample-ns]))

(def x "sample var")

(deftest var-name-works
  (is (= (namesp/var-name (var x))
         'yagni.namespace-test/x)))

(deftest qualified-interns-works
  (is (= (namesp/qualified-interns 'yagni.sample-ns)
         '(yagni.sample-ns/y
            yagni.sample-ns/x))))

(deftest named-functions-works
  (is (= (namesp/named-functions 'yagni.sample-ns)
         '(yagni.sample-ns/y))))

(deftest named-functions-map-works
  (is (= (namesp/named-functions-map ['yagni.sample-ns]) 
         {'yagni.sample-ns/y 0})))
