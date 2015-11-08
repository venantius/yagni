(ns yagni-test.core
  (:gen-class)
  (:require [secondns]))

(defn x
  "Sample function"
  [y]
  (secondns/other-func))

(defn ->y
  "This looks like a class constructor."
  [x]
  true)

(defn where-sample-slash-test-dispatches-to
  "This function is what gets consumed by our tagged literal."
  [x]
  true)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (x)
  (println "Hello, World!"))
