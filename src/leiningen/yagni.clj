(ns leiningen.yagni
  (:require [leiningen.core.eval :refer [eval-in-project]
             leiningen.core.project :refer [merge-profiles]]))

(defn yagni
  "I don't do a lot."
  [project & args]
  (let [opts (select-keys project [:source-paths :yagni])
        deps {:dependencies [['venantius/yagni "0.1.0"]]}]
    (eval-in-project
      (merge-profiles project [deps])
      `(yagni.core/run-yagni '~opts)
      '(require 'yagni.core))))
