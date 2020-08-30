(ns clj-budget.common
  (:require [clj-budget.util :refer [read-edn]]))

(def read-budgets (delay (read-edn "budget-data/budgets.edn")))

(defn all-budgets []
  (:budgets @read-budgets))

(defn selected-budget []
  (first (filter #(:selected %) (all-budgets))))