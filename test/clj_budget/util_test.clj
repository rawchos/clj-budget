(ns clj-budget.util-test
  (:require [clojure.test :refer [deftest is testing]]
            [clj-budget.util :as util]))

(deftest read-edn-test
  (testing "should read in the contents of an edn"
    (is (= (util/read-edn "test/files/read-test.edn")
           {:blah "something"}))))

(deftest format-size-test
  (let [coll '({:title "x"}
               {:title "xx"}
               {:title "xxx"}
               {:title "xxxx"}
               {:title "xxxx"})]
    (testing "should use the default amount if it is larger"
          (is (= (util/format-size :title coll 15) 15)))
    (testing "should use the largest size (plus 5) if default is smaller"
          (is (= (util/format-size :title coll 8) 9)))
    (testing "should use the base default if none is provided"
          (is (= (util/format-size :title coll) 10)))))