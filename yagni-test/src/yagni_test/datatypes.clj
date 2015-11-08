(ns yagni-test.datatypes
  "Test examples for defprotocol, deftype, defrecord")

(defprotocol UsedProtocol
  (foo [this]))

(deftype UsedTypeImplementingUsedProtocol []
  UsedProtocol
  (foo [this]
    true))

(deftype UnusedTypeImplementingUsedProtocol []
  UsedProtocol
  (foo [this]
    false))

(defrecord UsedRecord1 [])

(defrecord UsedRecord2 [])

(defrecord UnusedRecord [])

(deftype UnusedType [])

(defprotocol UnusedProtocol
  (flub [this]))

(defn sample
  []
  (new UsedRecord1)
  (UsedRecord2.)
  (foo (UsedTypeImplementingUsedProtocol.)))
