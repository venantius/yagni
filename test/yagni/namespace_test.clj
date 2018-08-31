(ns yagni.namespace-test
  (:require [clojure.test :refer :all]
            [yagni.namespace :as namesp]
            [yagni.sample-ns :as sample-ns]
            [yagni.sample-cond-ns :as sample-cond-ns]
            ))

(def x "sample var")

(deftest var-name-works
  (is (= (namesp/var-name (var x))
         'yagni.namespace-test/x)))

(deftest qualified-interns-works
  (is (= (sort (namesp/qualified-interns 'yagni.sample-ns))
         '(yagni.sample-ns/form-for-testing-get
           yagni.sample-ns/x
           yagni.sample-ns/y))))

(deftest named-vars-map-works
  (is (= (namesp/named-vars-map ['yagni.sample-ns])
         {'yagni.sample-ns/x #{}
          'yagni.sample-ns/form-for-testing-get #{}
          'yagni.sample-ns/y #{}})))

(deftest cljc-ns-works
  (is (= (namesp/named-vars-map ['yagni.sample-cond-ns])
         {'yagni.sample-cond-ns/x #{}
          'yagni.sample-cond-ns/form-for-testing-get #{}
          'yagni.sample-cond-ns/y #{}
          'yagni.sample-cond-ns/a #{}
          'yagni.sample-cond-ns/b #{}})))
