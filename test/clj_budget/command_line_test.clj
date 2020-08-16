(ns clj-budget.command-line-test
  (:require [clj-budget.command-line :as cli]
            [midje.sweet :refer [fact
                                 facts
                                 =>]]))

(facts "about 'strings->keywords'"
       (fact "should convert string keywords to actual keywords"
             (cli/strings->keywords {":blah" "hello" ":another-blah" "world"}) => {:blah "hello" :another-blah "world"}))

(facts "about 'argument-map'"
       (fact "should create an argument map from a list of values"
             (cli/argument-map [":rick" "sanchez" ":morty" "smith"]) => {:rick "sanchez" :morty "smith"}))