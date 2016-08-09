(ns learning-tic-tac-toe.core
  (:require [clojure.spec :as s]
            [clojure.spec.test :as test]))

(def move? #{"O" "X"})

(def cell? (conj move? ""))

(s/def ::line (s/tuple cell? cell? cell?))

(s/def ::board (s/tuple cell? cell? cell? 
                        cell? cell? cell? 
                        cell? cell? cell?))

(def possible-lines [[0 1 2] [3 4 5] [6 7 8] [0 4 8] 
                     [2 4 6] [0 3 6] [1 4 7] [2 5 8]])

(defn winning-line? 
  "Does this set of values indicate a win"
   [line]
   (or (every? #(= "X" %) line) 
       (every? #(= "O" %) line)))

(s/fdef winning-line?
        :args (s/cat :line ::line)
        :ret boolean? 
        :fn #(= (:ret %)
                (and (apply = (-> % :args :line))
                     (not (= ["" "" ""] (-> % :args :line))))))

(defn all-lines
  [board]
  (let [get-line (fn [cells] (map #(nth board %) cells))]
    (map get-line possible-lines)))

(defn winner? 
  [board]
  (some true? (map winning-line? (all-lines board))))
