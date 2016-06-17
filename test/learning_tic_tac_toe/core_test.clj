(ns learning-tic-tac-toe.core-test
  (:require [clojure.test :refer :all]
            [learning-tic-tac-toe.core :refer :all]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.properties :as prop]))

(defspec winning-line-returns-true-when-three-squares-match 
  100
  (prop/for-all [v (s/gen :learning-tic-tac-toe.core/line)]
                (= (= 3 (max (count (filter #(= "X" %) v))
                             (count (filter #(= "O" %) v)))) 
                   (winning-line? v))))

(defspec detect-winners 
  100
  (prop/for-all [v (s/gen :learning-tic-tac-toe.core/board)]
                (let [cell (partial nth v)
                      top [(cell 0) (cell 1) (cell 2)]
                      mid [(cell 3) (cell 4) (cell 5)]
                      bot [(cell 6) (cell 7) (cell 8)]
                      left [(cell 0) (cell 3) (cell 6)]
                      midv [(cell 1) (cell 4) (cell 7)]
                      rite [(cell 2) (cell 5) (cell 8)]
                      diag1 [(cell 0) (cell 4) (cell 8)]
                      diag2 [(cell 2) (cell 4) (cell 6)]]
                  (= (true? (or
                             (winning-line? top)
                             (winning-line? mid)
                             (winning-line? bot)
                             (winning-line? left)
                             (winning-line? midv)
                             (winning-line? rite)
                             (winning-line? diag1)
                             (winning-line? diag2)))
                     (true? (winner? v))))))
