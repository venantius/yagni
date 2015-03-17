(ns yagni.namespace
  "Functions for working with namespaces"
  (:require [clojure.test :refer [function?]]))

(defn prepare-namespaces
  "First, create all of our namespaces so that we dont' have to worry about
   the order in which we load them. Then, load all of them."
  [namespaces]
  (doseq [n namespaces]
    (create-ns n))
  (doseq [n namespaces]
    (require n :reload)))

(defn var-name
  "Return the fully qualified name of the corresponding var."
  [n v]
  (symbol (str n)
          (str (:name (meta v)))))

(defn qualified-interns
  "Return a seq of fully qualified namespace symbols"
  [n]
  (map #(var-name n (second %)) (ns-interns n)))

(defn named-functions
  "Return the named functions in this namespace"
  [n]
  (filter function? (qualified-interns n)))

(defn named-functions-map
  "Build a map of all named functions, with the values set to 0."
  [namespaces]
  (into {} (map vector 
                (flatten (map named-functions namespaces)) 
                (repeatedly (constantly 0)))))
