(require '[clojure.string :as s])

;; Exercise 1
(def counter (atom 1))
(dotimes [_ 5]
  (swap! counter inc))
@counter

;; Exercise 2
(def quotes (s/split-lines (slurp "quotes")))

(defn random-quote
  []
  (Thread/sleep 100)
  (rand-nth quotes))

(defn word-count
  [s]
  (let [words (re-seq #"[A-Za-z]+" s)]
    (frequencies (map s/lower-case words))))

(defn quote-word-count
  [n]
  (let [total (atom {})
        jobs (for [_ (range n)]
               (future (let [wc (word-count (random-quote))]
                         (swap! total #(merge-with + % wc)))))]
    (doseq [j jobs] @j)
    @total))

;; Exercise 3
(def char-a (ref {:hp 15 :max-hp 40}))
(def char-b (ref {:inventory #{:healing-potion}}))

(defn heal
  [healer healed]
  (dosync
   (when ((:inventory @healer) :healing-potion)
     (alter healer update :inventory disj :healing-potion)
     (alter healed assoc :hp (:max-hp @healed)))))
