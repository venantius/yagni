(ns yagni.namespace.form
  (:require [clojure.repl :refer [source-fn]]
            [yagni.jvm :as jvm]
            [yagni.namespace :refer [qualified-interns
                                     var-name]]))

(defn get-form
  "Retrieve the form for the underlying symbol"
  [s]
  (when-let [source (source-fn s)]
    {:source (read-string {:read-cond :allow} source)
     :sym s}))

(defn try-to-resolve
  "Tries to resolve the symbol in question. Catch any thrown exceptions and
   return nil in that case."
  [x]
  (try
    (if-let [c (jvm/is-class-constructor? x)]
      c
      (resolve x))
    (catch java.lang.ClassNotFoundException e
      nil)))

(defn maybe-inc
  "Take a look at the form. If it's a symbol that can be resolved to a var
   and exists as a node in our graph, then add an outgoing edge from the
   sym to this var."
  [graph sym form]
  (when (symbol? form)
    (when-let [form-var (try-to-resolve form)]
      (let [v (var-name form-var)]
        (when (and
               (get @graph v)
               (not= v sym))
          (swap! graph update-in [sym] conj v))))))

(defn walk-form-body
  "Walk the form."
  [graph {:keys [form sym]}]
  (if (or (seq? form) (coll? form))
    (when (seq form)
      (walk-form-body graph {:form (first form)
                             :sym sym})
      (walk-form-body graph {:form (rest form)
                             :sym sym}))
    (maybe-inc graph sym form)))

(defn macroexpand-source
  "While keeping track of the symbol whose form we're exploring, macroexpand
   the form's source."
  [{:keys [source sym]}]
  {:form (macroexpand source)
   :sym sym})

(defn count-vars-in-ns
  "Count the functions in a single ns."
  [g n]
  (let [interns (qualified-interns n)
        forms (map get-form interns)]
    (in-ns n)
    (doall
     (map
      (partial walk-form-body g)
      (map macroexpand-source forms)))))

(defn count-vars
  "Count the functions in all namespaces."
  [graph namespaces]
  (doall (map (partial count-vars-in-ns graph) namespaces)))
