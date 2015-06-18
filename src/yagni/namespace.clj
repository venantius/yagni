(ns yagni.namespace
  "Functions for working with namespaces")

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
  [v]
  (symbol (str (:ns (meta v)))
          (str (:name (meta v)))))

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
