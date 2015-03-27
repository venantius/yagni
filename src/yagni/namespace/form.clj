(ns yagni.namespace.form
  (:require [clojure.repl :refer [source-fn]]
            [yagni.namespace :refer [qualified-interns
                                     var-name]]))

(def fn-counter (atom {}))

(defn get-form
  "Retrieve the form for the underlying symbol"
  [s]
  (when (source-fn s)
    (read-string (source-fn s))))

(defn try-to-resolve
  "Tries to resolve the symbol in question. Catch any thrown exceptions and
   return nil in that case."
  [x]
  (try
    (resolve x)
    (catch java.lang.ClassNotFoundException e
      nil)))

(defn maybe-inc
  "Take a look at the form. If it's a symbol that can be resolved to a var,
   and is already within our function counter, then increment the counter."
  [form]
  (if (symbol? form)
    (if-let [form-var (try-to-resolve form)]
      (when (get @fn-counter (var-name form-var))
        (swap! fn-counter update-in [(var-name form-var)] inc)))))

(defn walk-form-body
  "Walk the form."
  [form]
  (if (or (seq? form) (coll? form))
    (when (seq form)
      (walk-form-body (first form))
      (walk-form-body (rest form)))
    (maybe-inc form)))

(defn count-fns-in-ns
  "Count the functions in a single ns."
  [n]
  (let [interns (qualified-interns n)
        forms (map get-form interns)]
    (in-ns n)
    (doall (map #(walk-form-body (macroexpand %)) forms))))

(defn count-fns
  "Count the functions in all namespaces."
  [namespaces]
  (doall (map count-fns-in-ns namespaces)))
