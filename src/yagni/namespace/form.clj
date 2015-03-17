(ns yagni.namespace.form
  (:require [clojure.repl :refer [source-fn]]
            [yagni.namespace :refer [qualified-interns]]))

(defn get-form
  "Retrieve the form for the underlying symbol"
  [s]
  (read-string (source-fn s)))

(defn debug
  [x]
  (do 
    (println x)
    (when 
      (symbol? x) 
      (resolve x))))

(defn walk-form
  "Walk the form"
  [form]
  (cond (empty? form) (println "")
        :else (do 
                (debug (first form))
                (walk-form (rest form)))))

(defn count-fns-in-ns
  "Count the functions in a single ns."
  [n fn-map]
  (let [interns (qualified-interns n)
        forms (map get-form interns)]
    (ns n)
    (walk-form (first forms))
    fn-map))

(defn count-fns
  "Count the functions in all namespaces."
  [namespaces fn-map]
  (if 
    (empty? namespaces) fn-map
    (count-fns (rest namespaces) 
               (count-fns-in-ns 
                 (first namespaces)
                 fn-map))))
