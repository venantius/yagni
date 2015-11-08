(ns secondns
  "A second NS outside the first source path")

(defn func
  []
  true)

(def notafunc false)

(defn func-the-second
  []
  notafunc)

(defn other-func
  []
  (func))
