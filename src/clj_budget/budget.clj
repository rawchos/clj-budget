(ns clj-budget.budget
  (:require [clj-budget.common :refer [all-budgets]]))

(defn list-budgets []
  (doseq [[budget selected] (map (juxt :budget-name :selected)
                                 (all-budgets))]
    (println (format "   %s %s"  budget (if selected "(selected)" "")))))
