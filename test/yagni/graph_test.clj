(ns test.yagni.graph-test
  (:require [clojure.test :refer :all]
            [yagni.graph :refer [prune-findable-nodes!]]))

(deftest prune-findable-nodes-works
  (let [graph (atom {:a #{:b}
                     :b #{}
                     :c #{:a}
                     :main #{:a}})
        found-nodes (atom #{})]
    (prune-findable-nodes! graph [:main] found-nodes)
    (is (= @graph
           {:c #{:a}}))
    (is (= @found-nodes
           #{:main :b :a}))))
