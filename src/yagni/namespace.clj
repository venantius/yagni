(ns yagni.namespace
  "Functions for working with namespaces"
  (:require [clojure.test :refer [function?]]))

(defn var-name
  "Return the fully qualified name of the corresponding var."
  [n v]
  (symbol (str n)
          (str (:name (meta v)))))

(defn named-functions
  "Return the named functions in this namespace"
  [n]
  (map #(var-name n (second %)) (ns-interns n)))

(defn named-functions-map
  "Build a map of all named functions, with the values set to 0."
  [namespaces]
  (into {} (map vector 
                (flatten (map named-functions namespaces)) 
                (repeatedly (constantly 0)))))
