(ns yagni.reporter
  (:require [clojure.tools.logging :as log]
            [yagni.jvm :as jvm]
            [yagni.graph :as graph]))

(defn report
  "Report on the output."
  [g]
  (let [{:keys [children parents]} (graph/find-children-and-parents @g)]
    (when (not-empty parents)
      (println
       (str
        "=================== WARNING: Parents ======================\n"
        "==    Could not find any references to the following     ==\n"
        "===========================================================\n"))
      (doall (map println parents)))
    (when (not-empty children)
      (println
       (str
        "\n"
        "================== WARNING: Children ======================\n"
        "==   The following have references to them, but their    ==\n"
        "==   parents do not.                                     ==\n"
        "===========================================================\n"))
      (doall (map println children)))
    (or (not-empty parents) (not-empty children))))
