(ns records_parser.api
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [records_parser.utils :as utils]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.util.response :as response]
            [clojure.data.json :as json]))

(defonce records (atom []))

(defn validate-record [record]
  (and (string? (:firstName record))
       (string? (:gender record))
       (string? (:dob record))
       (string? (:lastName record))
       (string? (:color record))))

(defn json-response [data]
  (-> (response/response (json/write-str data))
      (response/content-type "application/json")))

(defn format-date?
  [{:keys [lastName firstName gender color dob]} format?]
  {:lastName lastName
   :firstName firstName
   :gender gender
   :color color
   :dob (if format?
          (utils/format-date dob)
          (utils/unformat-date dob))})

;; routes

(defroutes api-routes
   (POST "/records" req
    (let [record (json/read-str (slurp (:body req)) :key-fn keyword)]
      (if (validate-record record)
        (do
          (swap! records conj record)
          (json-response {:message "Record added"}))
        (response/bad-request {:message "Invalid record format"}))))

  (GET "/records/" []
    (json-response {:body @records}))

  (GET "/records/gender" []
    (json-response (sort-by :gender @records)))

  (GET "/records/birthdate" []
    (let [records (map #(format-date? % true) @records)
          sorted-records (sort-by :dob records)
          unformatted-records (map #(format-date? % false) sorted-records)]
    (json-response unformatted-records)))

  (GET "/records/name" []
    (json-response (sort-by :lastName @records))))
