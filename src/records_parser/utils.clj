(ns records_parser.utils
  (:import [java.time LocalDate]
           [java.time.format DateTimeFormatter]))

(def formatter (DateTimeFormatter/ofPattern "MM/dd/yyyy"))

(defn get-year [d]
  (.getYear d))

(defn get-month [d]
  (.getMonth d))

(defn get-day [d]
  (.getDayOfMonth d))

(defn format-date [d]
  (LocalDate/parse d formatter))

(defn unformat-date [d]
  (str (.format d formatter)))
