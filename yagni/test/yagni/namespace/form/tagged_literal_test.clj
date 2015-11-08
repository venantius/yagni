(ns yagni.namespace.form.tagged-literal-test
  (:require [clojure.test :refer :all]
            [yagni.namespace.form.tagged-literal :as tl]))

(deftest matches-s-exp-char-works
  (are [x y] (= x y)
       "(" (tl/matches-s-exp-char "(")
       "#{" (tl/matches-s-exp-char "#{")
       ")" (tl/matches-s-exp-char ")")
       "}" (tl/matches-s-exp-char "}")
       "{" (tl/matches-s-exp-char "{")
       "[" (tl/matches-s-exp-char "[")
       "]" (tl/matches-s-exp-char "]")))
