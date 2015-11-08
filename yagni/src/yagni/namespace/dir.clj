(ns yagni.namespace.dir
  (:require [clojure.tools.namespace.dir :as dir]
            [clojure.tools.namespace.track :as track]
            [yagni.namespace.file :refer [canonical-filename]]))

(defn nss-in-dirs
  "Return a map containing a list of all the project's namespaces."
  [source-paths]
  (let [dirs (map canonical-filename source-paths)
        tracker (if (seq dirs)
                  (apply dir/scan-all (track/tracker) dirs)
                  (track/tracker))]
    (:clojure.tools.namespace.track/load tracker)))
