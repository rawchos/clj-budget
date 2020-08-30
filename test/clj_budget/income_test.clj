(ns clj-budget.income-test
  (:require [clj-budget.income :as i]
            [clj-budget.common :as c]
            [midje.sweet :refer [against-background
                                 fact
                                 facts
                                 =>]]))

(def test-budget
  {:budget-name "Test Budget"
   :incomes [{:income-name "First Income"}
             {:income-name "Second Income"}]})

(facts "about 'incomes'"
       (against-background [(c/selected-budget) => test-budget]
                           (fact "should return all incomes"
                                 (i/incomes) => (:incomes test-budget))))

(facts "about 'total-budgeted'"
       (fact "should total the amount in the :budget-amount field"
             (i/total-budgeted [{:budget-amount 25}
                                {:budget-amount 50}
                                {:budget-amount 25}]) => 100))