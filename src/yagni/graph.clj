(ns yagni.graph
  "Functions for dealing with graphs."
  (:require [clojure.set :as set]))

(defn- dfs
  "Using a depth-first search algorithm, explore the graph from the given
   starting node. Return what remains of the graph (should only include
   nodes that aren't findable from the starting node.)"
  [g n v]
  (swap! v conj n)
  (let [edges (remove @v (get @g n))]
    (swap! g dissoc n)
    (doall (map (fn [e] (dfs g e v)) edges))
    g))

(defn prune-findable-nodes!
  "Repeatedly search the graph from a list of initial endpoints, e. Remove
   all findable nodes from the graph, and add them to the set of found nodes."
  [g e v]
  (doall (map (fn [x] (dfs g x v)) e)))

(defn find-children-and-parents
  "Given a graph of 'orphaned' vars, figure out which of these vars have
   *something* referring to them (children), and which have nothing referring
   to them (parents)."
  [g]
  (let [edges (reduce into #{} (vals g))
        nodes (into #{} (keys g))
        children (set/intersection edges nodes)
        parents (set/difference nodes children)]
    {:children children
     :parents parents}))
