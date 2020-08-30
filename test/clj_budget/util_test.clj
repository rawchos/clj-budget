(ns clj-budget.util-test
  (:require [clj-budget.util :as util]
            [midje.sweet :refer [fact
                                 facts
                                 =>]]))
(facts "about 'read-edn'"
       (fact "should read in the contents of an edn"
             (util/read-edn "test/files/read-test.edn") => {:blah "something"}))

(facts "about 'format-size'"
       (let [coll '({:title "x"}
                    {:title "xx"}
                    {:title "xxx"}
                    {:title "xxxx"}
                    {:title "xxxx"})]
         (fact "should use the default amount if it is larger"
               (util/format-size :title coll 15) => 15)
         (fact "should use the largest size (plus 5) if default is smaller"
               (util/format-size :title coll 8) => 9)
         (fact "should use the base default if none is provided"
               (util/format-size :title coll) => 10)))