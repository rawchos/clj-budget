(ns clj-budget.allocation
  (:require [clj-budget.common :refer [selected-budget]]
            [clj-budget.util :refer [format-size]]
            [clojure.string :refer [capitalize]]))

(defn bimonthly-total [categories]
  (->> (map :bimonthly-amount categories)
       (reduce +)))

(defn print-summary [paycheck-amount categories]
  (let [total (bimonthly-total categories)]
    (println (format "\n\n  Available: %6d" paycheck-amount))
    (println (format "  Budgeted:  %6d" total))
    (println (format "  Remaining: %6d" (- paycheck-amount total)))))


(defn print-allocation-totals
  "Prints the totals for all category allocations. Accepts an initial width so
   this line will match up with category listings."
  [initial-width categories]
  (let [total (bimonthly-total categories)]
    (println (format (str "%-" initial-width "s %6d  %6d  %6d")
                     "Total"
                     total
                     (* 2 total)
                     (* 24 total)))))

(defn bimonthly-amount
  "Determines the bimonthly amount for a category based on whether the expense
   type is monthly or yearly."
  [{:keys [goal expense-type]}]
  (case expense-type
    :yearly (-> (/ goal 24)
                (Math/ceil)
                (int))
    :monthly (-> (/ goal 2)
                 (Math/ceil)
                 (int))
    goal))

(defn enrich-category
  "Enrich category data with computed data."
  [category]
  (assoc category :bimonthly-amount (bimonthly-amount category)))

(defn categories [budget]
  (->> (:categories budget)
       (map enrich-category)))

;; Display:
;; Category  BiMonthly  Monthly  Yearly  Goal (monthly or yearly)
;; TODO: Add some totals
(defn bimonthly-allocations
  "Takes all the categories from a selected budget and determines how much
   needs to be allocated bimonthly to achieve those budget amounts over the
   course of a year."
  ([] (bimonthly-allocations 0))
  ([paycheck-amount]
   (let [categories (categories (selected-budget))
         name-width (format-size :category-name categories)]
     (doseq [{:keys [category-name
                     bimonthly-amount
                     goal
                     expense-type]} (sort-by :bimonthly-amount > categories)]
       (println (format (str "%-" name-width "s %6d  %6d  %6d  %6d %s")
                        category-name
                        bimonthly-amount
                        (* 2 bimonthly-amount)
                        (* 24 bimonthly-amount)
                        goal
                        (-> (name expense-type) capitalize))))
     (print-allocation-totals name-width categories)
     (when (> paycheck-amount 0)
       (print-summary paycheck-amount categories)))))

(comment
  (bimonthly-allocations 5050)

  (categories (selected-budget))

  (bimonthly-amount {:goal 200 :expense-type :monthly})
  (bimonthly-amount {:goal 125 :expense-type :yearly})
  (bimonthly-amount {:goal 3000 :expense-type :yearly})
  (bimonthly-amount {:goal 5000 :expense-type :yearly}))