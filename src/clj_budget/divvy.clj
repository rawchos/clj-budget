(ns clj-budget.divvy
  (:require [clj-budget.util :refer [format-size
                                     read-json
                                     round
                                     write-json]]))

(defn categories-total
  "Determines the total of accruable categories. Only counts the accruable
   categories because we'll need to get accurate percentages for each accruable
   category and there may be categories listed that aren't accruable."
  [categories]
  (->> (map :amount categories)
       (reduce +)))

(defn adjust-category
  "Adjusts a category if it is an accruable category. Otherwise, returns the
   category unchanged."
  [total divvy-amount name-width {:keys [category-name
                                         accruable
                                         amount] :as category}]
  (if accruable
    (let [percentage (round 3 (/ amount total))
          adjustment (round 2 (* divvy-amount percentage))]
      (println (format (str "%-" name-width "s %7.2f") category-name adjustment))
      (assoc category :amount (+ amount adjustment)))
    category))

(defn divvy
  "Divvies up an amount across eligible accruable categories. The amount to
   add to each category will be proportional to its size within the overall
   account."
  [{:keys [categories] :as account} amount]
  (let [total (categories-total categories)
        name-width (format-size :category-name categories)
        adjust-fn (partial adjust-category total amount name-width)
        adjusted-categories (doall (map adjust-fn categories))]
    (assoc account :categories adjusted-categories)))

(comment
  (def savings-account (read-json "budget-data/savings.json"))
  (def divvy-amount 240.45)
  
  (def adjusted-account (divvy savings-account divvy-amount))
  (:categories adjusted-account)
  
  (write-json "budget-data/savings.json" adjusted-account)
  )