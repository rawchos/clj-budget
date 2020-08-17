(ns clj-budget.budget
  (:require [clj-budget.common :refer [all-budgets]]))

(defn list-budgets []
  (doseq [budget (map :budget-name (all-budgets))]
    (println (format "   %s"  budget))))