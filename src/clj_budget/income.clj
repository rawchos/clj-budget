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

(def tax-refund
  {:total 13996
   :payouts [{:bronco     5000}
             {:elton-john 1000}]
   :paybacks [{:bronco {:total       5000
                        :carpet      3000
                        :sofa        1000
                        :car-payment 1000}}
              {:elton-john {:total   1000
                            :savings 1000}}]})

(def the-bronco
  {:total 8000
   :take-from "truist checking"
   :paybacks {:carpet 3000
              :sofa 1000
              :car-payment 1000
              :tax-refund 2000
              :mortgage 3000}
   :maybe-paybacks {:neal-savings 5000}})

(defn print-incomes
  ([]
   (print-incomes :all))
  ([tag]
   (doseq [income (incomes tag)]
     (print-income income))))

; (print-incomes :first-quarterly)  ; feb
; (print-incomes :second-quarterly) ; may
; (print-incomes :third-quarterly)  ; aug
(comment
  (print-incomes :bonus) ; nov
  (print-incomes :first)
  (print-incomes :second)
  )

;; I have no idea what these were?
; "1fmee5dp4nla90377" 
; "1FMEE5DP4NLA90377"

; canada trip being taken from carpet. pay back to there
; from expense report

;; GOALS
;;
;;  Extra 150 each (300) to IRA (will bring to 6000/yr)
