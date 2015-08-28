(ns yagni.jvm-test
  (:require [clojure.test :refer :all]
            [yagni.jvm :as jvm]))

(defprotocol Foo
  (bar [this]))

(deftype FooType []
  Foo
  (bar [this] true))

(defrecord FooRecord [bar baz])

(defprotocol SampleInterface
  (foo [this]))

(defn ->non-class-converter
  []
  true)

(deftest class-name->var-name
  (is (= (jvm/class-name->var-name yagni.jvm_test.SampleInterface)
         'yagni.jvm-test/SampleInterface)))

(deftest var-name->class-name-works
  (is (= (jvm/var-name->class-name
          'yagni-test.datatypes/UnusedTypeImplementingUsedProtocol)
         'yagni_test.datatypes.UnusedTypeImplementingUsedProtocol)))

(deftest is-class-generator?-works
  (is (some? (jvm/is-class-generator? 'yagni.jvm-test/->FooType)))
  (is (some? (jvm/is-class-generator? 'yagni.jvm-test/->FooRecord)))
  (is (false? (jvm/is-class-generator? 'yagni.jvm-test/->non-class-converter)))
  (is (some? (jvm/is-class-generator? 'yagni.jvm-test/->non-class-converter)))
  (is (some? (jvm/is-class-generator? 'yagni.jvm-test/map->FooRecord))))

(deftest find-generator-fns-works
  (is (= #{'yagni.jvm-test/->FooType}
         (jvm/find-generator-fns (atom {'yagni.jvm-test/->FooType #{}})))))
