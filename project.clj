(defproject RecordsParser "0.1.0-SNAPSHOT"
  :description "A Clojure project to parse and sort a set of records"
  :url "http://example.com/record-sorter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clojure.java-time "1.2.0"]]  ; For date/time operations
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
