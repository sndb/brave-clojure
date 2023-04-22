(require '[clojure.string :as s])

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(s/split % #",")
       (s/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(def suspects (glitter-filter 3 (mapify (parse (slurp filename)))))

;; Exercise 1
(defn names
  [records]
  (map :name records))

(names suspects)

;; Exercise 2
(defn append
  [records new-record]
  (conj records new-record))

(append suspects {:name "New Suspect" :glitter-index 8})

;; Exercise 3
(defn validate
  [keywords-to-validators record]
  (every? (fn [[kw valid?]]
            (valid? (get record kw)))
          keywords-to-validators))

(def validators {:name string?
                 :glitter-index integer?})

(validate validators {:name "Carlisle Cullen", :glitter-index 6})

;; Exercise 4
(defn convert-back
  [records]
  (s/join "\n" (map #(s/join "," (map % vamp-keys)) records)))

(convert-back suspects)
