(ns yagni.core-test
  (:require [clojure.test :refer :all]
            [yagni.core :as core]))

(deftest load-entrypoints-works
  (let [vars (core/load-entrypoints)]
    (is (= (into [] vars) 
           ['yagni.samplens/samplevar 
            'yagni.samplens2/samplevar2
            'leiningen.yagni/yagni]))))

(deftest merge-entrypoints-works
  (let [main1 "yagni.core"
        main2 nil]
    (is (= (core/merge-entrypoints main1)
           ['yagni.samplens/samplevar 
            'yagni.samplens2/samplevar2
            'leiningen.yagni/yagni
            'yagni.core/-main]))
    (is (= (core/merge-entrypoints main2)
           ['yagni.samplens/samplevar 
            'yagni.samplens2/samplevar2
            'leiningen.yagni/yagni]))
    (with-redefs [yagni.core/load-entrypoints (constantly nil)]
      (is (= (core/merge-entrypoints main1)
             ['yagni.core/-main]))
      (is (= (core/merge-entrypoints main2)
             [])))))
