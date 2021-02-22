(ns clj-budget.income
  (:require [clj-budget.common :refer [selected-budget]]
            [clj-budget.util :refer [format-size]]))

(defn in? [coll k]
  (boolean (some #(= k %) coll)))

(defn incomes 
  ([]
   (incomes :all))
  ([tag]
   (->> (:incomes (selected-budget))
        (filter #(or (= tag :all)
                     (in? (:tags %) tag))))))

(defn income-names []
  (set (map :income-name (incomes))))

(defn list-incomes []
  (doseq [income-name (income-names)]
    (println (format "   %s" income-name))))

(defn total-budgeted [income-payouts]
  (->> (map :debit income-payouts)
       (reduce +)))

(defn print-income [{:keys [income-name
                            amount
                            payouts]}]
  (let [total (total-budgeted payouts)
        payout-width (format-size :category-name payouts)]
    (println (format "Name: %s" income-name))
    (println (format "  Amount:    %5d" amount))
    (println (format "  Budgeted:  %5d" total))
    (println (format "  Remaining: %5d" (- amount total)))
    (doseq [{:keys [category-name debit]} (sort-by :debit > payouts)]
      (println (format (str "    %-" payout-width "s %5d")
               category-name
               debit)))))

(defn print-incomes
  ([]
   (print-incomes :all))
  ([tag]
   (doseq [income (incomes tag)]
     (print-income income))))
