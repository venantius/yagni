(ns yagni.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [yagni.graph :as graph]
            [yagni.jvm :as jvm]
            [yagni.namespace.dir :refer [nss-in-dirs]]
            [yagni.namespace.form :as form]
            [yagni.namespace :as namesp]
            [yagni.reporter :refer [report]]))

(defn load-entrypoints
  "Checks to see if there's a `.lein-yagni` file in the project root; if so,
   load it."
  []
  (when (.exists (io/file ".lein-yagni"))
    (map symbol (string/split-lines (slurp ".lein-yagni")))))

(defn merge-entrypoints
  "Merge the main method (if it exists) with any entrypoints from the
   `.lein-yagni` file (if it exists)."
  [main]
  (into [] (concat
            (load-entrypoints)
            (when main [(symbol (str main) "-main")]))))

(defn construct-reference-graph
  "Build a graph of variable references within the provided source-paths,
  with escape hatches for the provided main method."
  [source-paths & [main]]
  (let [namespaces (nss-in-dirs source-paths)
        entrypoints (merge-entrypoints main)]
    (namesp/prepare-namespaces namespaces)
    (let [graph (atom (namesp/named-vars-map namespaces))
          generator-fns (jvm/find-generator-fns graph)]
      (jvm/extend-generators! graph generator-fns)
      (form/count-vars graph namespaces)
      (graph/prune-findable-nodes! graph entrypoints (atom #{}))
      (jvm/compress-generators! graph generator-fns)
      graph)))

(defn run-yagni
  "Main Yagni function.

   Yagni works by keeping track of all interned vars defined in a project's
   :source-paths directories' namespaces, and then macroexpanding all the
   forms in those namespaces to see where those are actually used.

   As it walks the forms, it builds a graph of var references. Once it's done
   building the graph, it searches the graph and emits warnings for nodes
   (vars) that weren't reachable from a search of entry points. By default,
   the only entry point is the project's `:main` method, but additional entry
   points can be specified in a `.lein-yagni` file at the root directory of
   the project."
  [{:keys [source-paths main] :as opts}]
  (let [graph (construct-reference-graph source-paths main)
        has-unused-vars? (report graph)]
    (shutdown-agents)
    (when has-unused-vars?
      (System/exit 1))))
