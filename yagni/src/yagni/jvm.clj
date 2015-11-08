(ns yagni.jvm
  "Utility functions for working with the JVM."
  (:require [clojure.reflect :refer [resolve-class]]
            [clojure.string :as string]))

(defn class-name->var-name
  "Return the var name for the given class name.

   Note that this makes NO assumptions about whether or not the class in
   question actually has an associated var.

   For reference, `defprotocol` does intern such a var, but `deftype` and
   `defrecord` do not (they only intern generator functions).

   E.g. let's assume we've got a namespace foo with (defprotocol bar ...) and
   (deftype baz [] bar ...). Macroexpanding baz will substitute foo.bar for
   foo/bar and will point to the associated compiled JVM bytecode."
  {:added "0.1.2"}
  [v]
  (let [c (-> v str (string/split #" ") second (string/split #"\."))
        n (string/replace (string/join "." (take (- (count c) 1) c)) "_" "-")
        v (last c)]
    (symbol n v)))

(defn var-name->class-name
  "Given a var name, try to find the equivalent class name."
  [v]
  (let [v (-> v str (string/split #"/"))
        n (string/replace (first v) "-" "_")
        c (last v)]
    (symbol (string/join "." [n c]))))

(defn can-resolve-class?
  [c]
  (try
    (resolve c)
    (catch ClassNotFoundException e
      false)))

(defn is-class-generator?
  "Check to see if a given var is a class generator. If it is, returns the
   Java class for the generator in question.

   This function is intended for dealing with the class generator functions
   that are interned by `deftype` and `defrecord` declarations."
  [v]
  (let [v (resolve v)
        n (str (:ns (meta v)))
        name (str (:name (meta v)))]
    (or
     (and
      (.startsWith name "->")
      (can-resolve-class? (var-name->class-name (symbol n (subs name 2)))))
     (and
      (.startsWith name "map->")
      (can-resolve-class? (var-name->class-name (symbol n (subs name 5))))))))

(defn is-class-constructor?
  "Check to see if a given symbol is a class constructor (e.g. `(String.)`)"
  [s]
  (let [s (str s)]
    (when (> (count s) 1)
      (and (.endsWith s ".")
           (resolve (symbol (subs s 0 (- (count s) 1))))))))

(defn find-generator-fns
  [g]
  (into #{} (filter is-class-generator? (keys @g))))

(defn extend-generators!
  "Given generator functions, add nodes for the corresponding `deftype` or
   `defrecord` and add edges from the generator functions to those
   nodes.

   It's worth noting that `deftype` and `defrecord` forms don't intern
   vars for themselves (just their generators). The fact that we add nodes
   for them is an exception to the normal mode of operation here."
  [g fns]
  (doseq [f fns]
    (when-let [v (class-name->var-name
                  (is-class-generator? f))]
      (swap! g assoc v #{})
      (swap! g update-in [f] conj v))))

(defn compress-generators!
  "Given a set of generator functions, compress the graph so that any
   edges to generator functions point instead to the 'vars' for the
   corresponding class objects, and any nodes for generator functions
   are removed."
  [g fns]
  (doseq [[k v] @g]
    (doseq [f v]
      (when-let [c (is-class-generator? f)]
        (swap! g update-in [k] conj (class-name->var-name c))
        (swap! g update-in [k] disj f))))
  (doseq [[k v] @g]
    (when-let [c (is-class-generator? k)]
      (swap! g dissoc k)
      (let [var-name (class-name->var-name c)]
        (swap! g update-in [var-name] clojure.set/union v)
        (swap! g update-in [var-name]
               disj var-name)))))
