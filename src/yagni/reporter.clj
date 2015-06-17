(ns yagni.reporter)

(defn report-item
  "Should I report on this item?"
  [[fn-name counter]]
  (when (= counter 1)
    (println "WARNING:" fn-name "doesn't seem to be called anywhere.")))

(defn report
  "Report on the output"
  [counter]
  (println counter)
  (doall (map report-item counter)))
