(ns clj-budget.scratch-pad2)

(defn towers-of-hanoi [n from to via]
  (when (pos? n)
    (towers-of-hanoi (dec n) from via to)
    (printf "Move from %s to %s\n" from to)
    (recur (dec n) via to from)))

(defrecord Node [el left right])

(defn bst-insert [{:keys [el left right] :as tree} value]
  (cond
    (nil? tree) (Node. value nil nil)
    (< value el) (Node. el (bst-insert left value) right)
    (> value el) (Node. el left (bst-insert right value))
    :else tree))

(defn bst-min [{:keys [el left]}]
  (if left (recur left) el))

(defn bst-max [{:keys [el right]}]
  (if right (recur right) el))

(defn bst-remove [{:keys [el left right] :as tree} value]
  (cond
    (nil? tree) nil
    (< value el) (Node. el (remove left value) right)
    (> value el) (Node. el left (remove right value))
    (nil? left) right
    (nil? right) left
    :else (let [min-value (bst-min right)]
            (Node. min-value left (bst-remove right min-value)))))

(def to-tree #(reduce bst-insert nil %))