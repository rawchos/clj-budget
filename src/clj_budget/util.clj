(ns clj-budget.util
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.io PushbackReader)))

(defn spit-edn [filename x]
  (spit filename (with-out-str (prn x))))

(defn read-edn [filename]
  (edn/read (PushbackReader. (io/reader filename))))

(defn format-size
  ([k coll]
   (format-size k coll 10))
  ([k coll min]
   (->> (map k coll)
        (map count)
        (map #(+ 5 %))
        (apply sorted-set-by > min)
        (first))))
