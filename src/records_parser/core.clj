(ns records_parser.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [records_parser.api :refer [api-routes]]
            [ring.adapter.jetty :as jetty]
            [records_parser.utils :as utils])
  (:gen-class))

(defn validate-record
  [fields]
  (when (= 5 (count fields))
    fields))

(defn comma-parser
  [record]
  (let [fields (str/split record #"\s*,\s*")]
    (if-let [[lastName firstName gender color dob] (validate-record fields)]
      {:lastName lastName
       :firstName firstName
       :gender gender
       :color color
       :dob (utils/format-date dob)})))

(defn pipe-parser
  [record]
  (let [fields (str/split record #"\s*\|\s*")]
    (if-let [[lastName firstName gender color dob] (validate-record fields)]
      {:lastName lastName
       :firstName firstName
       :gender gender
       :color color
       :dob (utils/format-date dob)})))

(defn space-parser
  [record]
  (let [fields (str/split record #"\s+")]
    (if-let [[lastName firstName gender color dob] (validate-record fields)]
      {:lastName lastName
       :firstName firstName
       :gender gender
       :color color
       :dob (utils/format-date dob)})))

(defn read-records [file parser]
  (with-open [rdr (io/reader file)]
    (doall (map parser (line-seq rdr)))))

(defn parsed-records
  [comma-file pipe-file space-file]
  (let [comma-records (read-records comma-file comma-parser)
        pipe-records (read-records pipe-file pipe-parser)
        space-records (read-records space-file space-parser)]
    (mapcat seq [comma-records pipe-records space-records])))

(defn sorted-by-gender-and-last-name
  [records]
  (->> records
       (sort-by :gender)
       (sort-by :lastName)))

(defn sorted-by-dob
  [records]
  (sort-by :dob records))

(defn sorted-by-last-name-desc
  [records]
  (reverse (sort-by :lastName records)))

(defn print-records
  [print-msg records sort-fn]
  (println print-msg)
  (doseq [record (sort-fn records)]
    (println (str (:lastName record) " "
                  (:firstName record) " "
                  (:gender record) " "
                  (:color record) " "
                  (utils/unformat-date (:dob record))))))

(defn records-parser
  [comma-file pipe-file space-file]
  (let [records (parsed-records comma-file pipe-file space-file)]
    (print-records "\nsorted by gender & then by last name :" records sorted-by-gender-and-last-name)
    (print-records "\nsorted by birth date :" records sorted-by-dob)
    (print-records "\nsorted by last name desc : " records sorted-by-last-name-desc)
    ))

(defn -main
  [& args]
  (jetty/run-jetty api-routes {:port 3000 :join? false}))