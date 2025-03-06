(ns clj-budget.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [clj-budget.core :as core]))

(deftest contrived-test
  (testing "the contrived test should return true"
        (is (true? (core/contrived-test)))))
