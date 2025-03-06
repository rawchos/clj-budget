(ns clj-budget.income-test
  (:require [clojure.test :refer [deftest is testing]]
            [clj-budget.income :as i]
            [clj-budget.common :as c]))

(def test-budget
  {:budget-name "Test Budget"
   :incomes [{:income-name "First Income"}
             {:income-name "Second Income"}]})

(deftest incomes-test
  (with-redefs [c/selected-budget (constantly test-budget)]
    (testing "should return all incomes"
          (is (= (i/incomes)
                 (:incomes test-budget))))))

(deftest total-budgeted-test
  (testing "should total the amount in the :budget-amount field"
        (is (= (i/total-budgeted [{:debit 25}
                                  {:debit 50}
                                  {:debit 25}])
               100))))