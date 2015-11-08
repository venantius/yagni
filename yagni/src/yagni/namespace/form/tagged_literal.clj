(ns yagni.namespace.form.tagged-literal
  "Functions dealing with tagged literals, in particular parse-tree
  transformations."
  (:require [instaparse.core :as insta]))

(defn return-nil
  [& args]
  nil)

(defn matches-s-exp-char
  [x]
  (when (string? x)
    (re-matches #"[\[\]\(\){}]|\#\{" x)))

(defn remove-nils-and-misc-s-exp-chars
  [& args]
  (->> args
       (remove nil?)
       (remove matches-s-exp-char)
       seq))

(defn find-literals
  "Given a parse tree, extract the tagged literals."
  {:added "0.1.5"}
  [d]
  (insta/transform
    {:file remove-nils-and-misc-s-exp-chars
     :form identity
     :reader_macro identity
     :forms remove-nils-and-misc-s-exp-chars
     :vector remove-nils-and-misc-s-exp-chars
     :list remove-nils-and-misc-s-exp-chars
     :map remove-nils-and-misc-s-exp-chars
     :set remove-nils-and-misc-s-exp-chars
     :dispatch str
     :ns_symbol identity
     :symbol identity
     :simple_sym identity
     :tag return-nil
     :comment return-nil
     :unquote return-nil
     :deref return-nil
     :backtick return-nil
     :gensym return-nil
     :quote return-nil
     :discard return-nil
     :unquote_splicing return-nil
     :var_quote return-nil
     :lambda return-nil
     :whitespace return-nil
     :literal return-nil}
    (vec d)))
