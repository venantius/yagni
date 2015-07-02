(defproject venantius/yagni "0.1.2-SNAPSHOT"
  :description "A Leiningen plugin for finding dead code."
  :url "https://github.com/venantius/yagni"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[im.chit/hara.class "2.1.8"]
                 [org.clojure/clojure "1.6.0"]
                 [org.clojure/tools.analyzer.jvm "0.6.7"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [org.slf4j/slf4j-log4j12 "1.7.12"]]
  :eval-in-leiningen true)
