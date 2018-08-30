(ns yagni.sample-cond-ns)

(def x "not a function")

(defn y [] x)

(defn form-for-testing-get
  [& args]
  (+ 1 2 3 (first args)))


#?(:clj (defn a []
          "clj")
   :cljs (defn a []
           "cljs"))

(defn b []
  (a))
