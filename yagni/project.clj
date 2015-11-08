(defproject venantius/yagni "0.1.5-SNAPSHOT"
  :description "A Leiningen plugin for finding dead code."
  :url "https://github.com/venantius/yagni"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[im.chit/hara.class "2.1.8"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [venantius/glow "0.1.2"]]
  :eval-in-leiningen true)
