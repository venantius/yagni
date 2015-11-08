(ns yagni-test.fullname)

(defn foo
  []
  true)

(defn bar?
  []
  (yagni-test.fullname/foo))
