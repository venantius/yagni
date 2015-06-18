(ns yagni.namespace.form
  (:require [clojure.repl :refer [source-fn]]
            [yagni.namespace :refer [qualified-interns
                                     var-name]]))

(def fn-graph (atom {}))

(defn get-form
  "Retrieve the form for the underlying symbol"
  [s]
  (when (source-fn s)
    {:source
     (read-string (source-fn s))
     :sym s}))

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
   and is already within our function counter, then add it to the counter."
  [sym form]
  (when (symbol? form)
    (when-let [form-var (try-to-resolve form)]
      (let [v (var-name form-var)]
        (when (and
                (get @fn-graph v)
                (not= v sym))
          (swap! fn-graph update-in [v] conj sym))))))

(defn walk-form-body
  "Walk the form."
  [{:keys [form sym]}]
  (if (or (seq? form) (coll? form))
    (when (seq form)
      (walk-form-body {:form (first form)
                       :sym sym})
      (walk-form-body {:form (rest form)
                       :sym sym}))
    (maybe-inc sym form)))

(defn macroexpand-source
  "While keeping track of the symbol whose form we're exploring, macroexpand
   the form's source."
  [{:keys [source sym]}]
  {:form (macroexpand source)
   :sym sym})

(defn count-fns-in-ns
  "Count the functions in a single ns."
  [n]
  (let [interns (qualified-interns n)
        forms (map get-form interns)]
    (in-ns n)
    (doall (map walk-form-body (map macroexpand-source forms)))))

(defn count-fns
  "Count the functions in all namespaces."
  [namespaces]
  (doall (map count-fns-in-ns namespaces)))
