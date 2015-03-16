(ns yagni.core
  (:require [yagni.namespace.dir :refer [nss-in-dirs]]))


(defn run-yagni
  "Main YAGNI function. Iterates through all the namespaces in the project's
   :source-paths directories.
   
   As it goes, it builds a map of (a) all named functions and (b) all called
   functions.
   
   Functions that are named and not called are enumerated at the end.
   
   TODO: Build in an escape hatch for :main methods.)
   "
  [{:keys [source-paths] :as opts}]
  (let [{:keys [namespaces]} (nss-in-dirs source-paths)]
    (doseq [n namespaces]
      (create-ns n))
    (println namespaces)
    (shutdown-agents)))
