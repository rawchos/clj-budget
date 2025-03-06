(ns clj-budget.command-line-test
  (:require [clojure.test :refer [deftest is testing]]
            [clj-budget.command-line :as cli]))

(deftest strings->keywords-test
  (testing "should convert string keywords to actual keywords"
    (is (= (cli/strings->keywords {":blah" "hello" ":another-blah" "world"})
           {:blah "hello" :another-blah "world"}))))

(deftest argument-map-test
  (testing "should create an argument map from a list of values"
    (is (= (cli/argument-map [":rick" "sanchez" ":morty" "smith"])
           {:rick "sanchez" :morty "smith"}))))