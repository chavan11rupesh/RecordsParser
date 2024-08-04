(defproject RecordsParser "0.1.0-SNAPSHOT"
  :description "A Clojure project to parse and sort a set of records"
  :url "http://example.com/record-sorter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clojure.java-time "1.2.0"]
                 [org.clojure/tools.cli "1.0.206"]
                 [ring "1.9.5"]
                 [compojure "1.6.2"]
                 [ring/ring-core "1.9.3"]
                 [ring/ring-jetty-adapter "1.9.3"]
                 [org.clojure/data.json "2.4.0"]]
  :target-path "target/%s"
  :main ^:skip-aot records_parser.core
  :profiles {:uberjar {:aot :all}})
