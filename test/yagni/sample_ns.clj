(ns yagni.sample-ns
  "A namespace for running tests against")

(def x "not a function")

(defn y [] x)

(defn form-for-testing-get
  [& args]
  (+ 1 2 3 (first args)))
