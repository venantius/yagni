(ns yagni.reporter
  (:require [clojure.tools.logging :as log]))

(defn report-item
  "If this function isn't called anywhere, emit a warning."
  [[fn-name edges]]
  (when (= (count edges) 0)
    (println "WARNING:" fn-name "doesn't seem to be called anywhere.")))

(defn report
  "Report on the output"
  [graph]
  (println graph)
  (doall (map report-item graph)))
