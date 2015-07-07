(ns leiningen.yagni
  (:require [leiningen.core.eval :refer [eval-in-project]]
            [leiningen.core.project :refer [merge-profiles]]))

(defn yagni
  "Pull out the source-paths and any YAGNI-specific options, and invoke our
   walker."
  [project & args]
  (let [opts (select-keys project [:source-paths :yagni :main])
        deps {:dependencies [['venantius/yagni "0.1.2-SNAPSHOT"]]}]
    (eval-in-project
      (merge-profiles project [deps])
      `(yagni.core/run-yagni '~opts)
      '(require 'yagni.core))))
