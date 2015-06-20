(ns yagni.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [yagni.graph :refer [prune-findable-nodes!]]
            [yagni.namespace.dir :refer [nss-in-dirs]]
            [yagni.namespace.form :refer [graph count-vars]]
            [yagni.namespace :refer [named-vars-map prepare-namespaces]]
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
  (let [namespaces (nss-in-dirs source-paths)
        entrypoints (merge-entrypoints main)
        found-nodes (atom #{})]
    (prepare-namespaces namespaces)
    (swap! graph merge (named-vars-map namespaces))
    (count-vars namespaces)
    (prune-findable-nodes! graph entrypoints found-nodes)
    (report graph)
    (shutdown-agents)))
