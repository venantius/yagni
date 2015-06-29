(ns yagni.namespace
  "Functions for working with namespaces"
  (:require [clojure.string :as string]
            [hara.class :refer [interface?]]))

(defn prepare-namespaces
  "First, create all of our namespaces so that we dont' have to worry about
   the order in which we load them. Then, load all of them."
  [namespaces]
  (doseq [n namespaces]
    (create-ns n))
  (doseq [n namespaces]
    (require n :reload)))

(defn interface->var-name
  "Return the var name for the given interface, assuming one exists.

   E.g. let's assume we've got a namespace foo with (defprotocol bar ...) and
   (deftype baz [] bar ...). Macroexpanding baz will substitute foo.bar for
   foo/bar and will point to the associated compiled JVM bytecode.
   
   If the var in question cannot be found (i.e. corresponds to native Java
   code), return nil."
  {:added "0.1.2"}
  [v]
  (let [c (-> v str (string/split #" ") second (string/split #"\."))
        n (string/replace (string/join "." (take (- (count c) 1) c)) "_" "-")
        v (last c)]
    (symbol n v)))

(defn var-name
  "Return the fully qualified name of the corresponding var.
   
   Includes checks for things like interfaces, which may exist as Clojure vars
   but must be checked "
  [v]
  (cond
    (and (class? v) (interface? v))
    (interface->var-name v)
    :else
    (symbol (str (:ns (meta v)))
            (str (:name (meta v))))))

(defn qualified-interns
  "Return a seq of fully qualified namespace symbols"
  [n]
  (map var-name (map second (ns-interns n))))

(defn named-vars-map
  "Build a map of all named vars, with the values set to an empty set."
  [namespaces]
  (into {} (map vector
                (flatten (map qualified-interns namespaces))
                (repeatedly (constantly #{})))))
