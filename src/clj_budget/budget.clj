(ns clj-budget.budget
  (:require [clj-budget.common :refer [all-budgets]]))

(defn list-budgets []
  (apply println (map :budget-name (all-budgets))))