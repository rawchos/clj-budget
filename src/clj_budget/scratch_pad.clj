(ns clj-budget.scratch-pad
  (:require [clojure.data :as data]))

(defn add-words [tree words]
  (reduce (fn [tree word]
            (assoc-in tree (concat word [:word]) word))
          tree
          words))

(defn match-words [tree prefix]
  (letfn [(search [node]
            (mapcat (fn [[k v]]
                      (if (= k :word)
                        [v]
                        (search v)))
                    node))]
    (search (get-in tree prefix))))

(def my-tree (add-words {} ["blah" "blah-two" "thing" "something"]))
(match-words my-tree "bl")

;; tic tac toe

(def game-board {:players {:X {:name "Player 1"}
                           :O {:name "Player 2"}}
                 :game-moves {:X #{}
                              :O #{}}
                 :next-turn :X})

;; Assume board of:
;; [:1 :2 :3
;;  :4 :5 :6
;;  :7 :8 :9]

(def winning-rows [#{:1 :2 :3}
                   #{:4 :5 :6}
                   #{:7 :8 :9}])

(def winning-columns [#{:1 :4 :7}
                      #{:2 :5 :8}
                      #{:3 :6 :9}])

(def winning-diags [#{:1 :5 :9}
                    #{:3 :5 :7}])

(def winning-combos (concat winning-rows winning-columns winning-diags))

(defn has-won? [moves]
  (->> (map (fn [winning-combo]
              (-> (data/diff winning-combo moves)
                  first
                  count
                  zero?))
            winning-combos)
       (filter true?)
       first
       boolean))

(defn check-winner [{:keys [game-moves] :as game-board}]
  (cond
    (has-won? (:X game-moves)) (str (-> game-board :players :X :name) " has won!")
    (has-won? (:O game-moves)) (str (-> game-board :players :O :name) " has won!")
    :else "The game continues..."))

(data/diff #{:1 :2 :3} #{:1 :3 :2 :4})

(has-won? #{:1 :5 :7 :6 :2})
(check-winner {:players {:X {:name "Player 1"}}
               :game-moves {:X #{:1 :2}}})
