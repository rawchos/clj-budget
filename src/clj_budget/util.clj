(ns clj-budget.util
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [cheshire.core :refer [generate-string
                                   parse-string]])
  (:import (java.io PushbackReader)))

(defn spit-edn [filename x]
  (spit filename (with-out-str (prn x))))

(defn read-edn [filename]
  (edn/read (PushbackReader. (io/reader filename))))

(defn json->edn [thing]
  (parse-string thing true))

(defn edn->json [thing]
  (generate-string thing {:pretty true}))

(defn read-json
  "Reads a specified json file and converts it to edn."
  [filename]
  (-> (slurp filename)
      (json->edn)))

(defn write-json
  "Converts edn to json and writes it to the specified file. Overwrites the
   file if it already exists."
  [filename m]
  (->> (edn->json m)
       (spit filename)))

(defn format-size
  ([k coll]
   (format-size k coll 10))
  ([k coll min]
   (->> (map k coll)
        (map count)
        (map #(+ 5 %))
        (apply sorted-set-by > min)
        (first))))

(defn round
  "Rounds a number to the specified precision."
  [precision n]
  (-> (str "%." precision "f")
      (format n)
      (Double/parseDouble)))

(comment
  (edn->json {:blah "thing"})
  (read-json "budget-data/savings.json")
  (def my-thing (read-json "budget-data/savings.json"))
  (write-json "budget-data/savings-bkup.json" my-thing)
  (round 2 13.1234)
  )
