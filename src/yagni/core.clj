(ns yagni.core
  (:require [yagni.namespace.dir :refer [nss-in-dirs]]
            [yagni.namespace.form :refer [graph count-vars]]
            [yagni.namespace :refer [named-vars-map prepare-namespaces]]
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
  (println opts)
  (let [namespaces (nss-in-dirs source-paths)]
    (prepare-namespaces namespaces)
    (swap! graph merge (named-vars-map namespaces))
    (count-vars namespaces)
    (report @graph)
    (shutdown-agents)))
