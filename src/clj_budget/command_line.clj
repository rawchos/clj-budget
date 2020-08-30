(ns clj-budget.command-line
  (:require [clj-budget.budget :refer [list-budgets]]
            [clj-budget.income :refer [list-incomes]]))

(defn unknown-function [function-key]
  (println (format "No function defined for key: %s" function-key)))

(def cli-functions
  {:list-budgets list-budgets
   :list-incomes list-incomes})

(defn cli-function [function-key]
  (let [skey (read-string function-key)]
    (if-let [f (skey cli-functions)]
      f
      (partial unknown-function function-key))))

;; TODO: If it isn't actually a keyword after calling read-string, maybe
;;       we should leave it as a string.
(defn strings->keywords 
  "When we receive our arguments from the command line, they're intended
   to be keywords but they're in the form of strings. We need to convert
   them to actual keywords."
  [arg-map]
  (reduce-kv (fn [m k v]
               (assoc m (read-string k) v)) {} arg-map))

(defn argument-map [args]
  (-> (apply hash-map args)
      (strings->keywords)))

(defn -main [& args]
  (let [f (cli-function (first args))]
    (if (seq (rest args))
      (f (argument-map (rest args)))
      (f))))
