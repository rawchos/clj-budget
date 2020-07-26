(ns util.core-test
  (:require [util.core :as util]
            [midje.sweet :refer [fact
                                 facts
                                 =>]]))
(facts "about 'read-edn'"
       (fact "should read in the contents of an edn"
             (util/read-edn "test/files/read-test.edn") => {:blah "something"}))