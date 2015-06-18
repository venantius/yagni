(ns yagni.core
  (:require [yagni.namespace.dir :refer [nss-in-dirs]]
            [yagni.namespace.form :refer [fn-graph count-fns]]
            [yagni.namespace :refer [named-functions-map prepare-namespaces]]
            [yagni.reporter :refer [report]]))

(defn run-yagni
  "Main Yagni function.

   Yagni works by keeping track of all functions defined in a project's
   :source-paths directories, and then macroexpanding all forms in the project
   to see where those functions are actually used.

   Yagni prints a warning for every function that don't have a reference
   within the application.

   Functions that are named and not called are enumerated at the end.

   TODO: Build in an escape hatch for :main methods."
  [{:keys [source-paths] :as opts}]
  (let [namespaces (nss-in-dirs source-paths)]
    (prepare-namespaces namespaces)
    (swap! fn-graph merge (named-functions-map namespaces))
    (count-fns namespaces)
    (report @fn-graph)
    (shutdown-agents)))
