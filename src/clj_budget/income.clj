(ns clj-budget.income
  (:require [clj-budget.common :refer [selected-budget]]
            [clj-budget.util :refer [format-size]]))

(defn incomes []
  (:incomes (selected-budget)))

(defn income-names []
  (set (map :income-name (incomes))))


(defn list-incomes []
  (doseq [income-name (income-names)]
    (println (format "   %s" income-name))))

(defn total-budgeted [income-payouts]
  (->> (map :budget-amount income-payouts)
       (reduce +)))

(defn print-income [{:keys [income-name
                            amount
                            payouts]}]
  (let [total (total-budgeted payouts)]
    (println (format "  Name: %s" income-name))
    (println (format "  Amount: %5d" amount))
    (println (format "  Budgeted: %5d" total))))
