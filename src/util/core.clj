(ns util.core
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io])
  (:import (java.io PushbackReader)))

(defn spit-edn [filename x]
  (spit filename (with-out-str (prn x))))

(defn read-edn [filename]
  (edn/read (PushbackReader. (io/reader filename))))