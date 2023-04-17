;; Exercise 1
(str "hello " "world")
(vector "hello" "world")
(list "hello" "world")
(hash-map :first "hello" :second "world")
(hash-set "hello" "world")

;; Exercise 2
(defn inc-maker
  "Create a custom incrementor."
  [inc-by]
  #(+ % inc-by))

(def add100 (inc-maker 100))

;; Exercise 3
(defn dec-maker
  "Create a custom decrementor."
  [dec-by]
  #(- % dec-by))

;; Exercise 4
(defn mapset
  "Works like map except the return value is a set."
  [f coll]
  (reduce (fn [a v] (conj a (f v))) #{} coll))

;; Exercise 5
(def asym-alien-body-parts [{:name "head" :size 3}
                            {:name "first-eye" :size 1}
                            {:name "first-ear" :size 1}
                            {:name "mouth" :size 1}
                            {:name "nose" :size 1}
                            {:name "neck" :size 2}
                            {:name "first-shoulder" :size 3}
                            {:name "first-upper-arm" :size 3}
                            {:name "chest" :size 10}
                            {:name "back" :size 10}
                            {:name "first-forearm" :size 3}
                            {:name "abdomen" :size 6}
                            {:name "first-kidney" :size 1}
                            {:name "first-hand" :size 2}
                            {:name "first-knee" :size 2}
                            {:name "first-thigh" :size 4}
                            {:name "first-lower-leg" :size 3}
                            {:name "first-achilles" :size 1}
                            {:name "first-foot" :size 2}])

(defn quintize-body-part
  [body-part]
  (conj (mapset (fn [sub]
                  (update body-part
                          :name
                          #(clojure.string/replace % #"^first-" sub)))
                ["second-" "third-" "fourth-" "fifth-"])
        body-part))

(defn quintize-body-parts
  "Similar to symmetrize-body-parts except that it has to work with
  weird space aliens with radial symmetry."
  [body-parts]
  (loop [result []
         remaining body-parts]
    (if (empty? remaining)
      result
      (let [[head & tail] remaining]
        (recur (into result (quintize-body-part head)) tail)))))

;; Exercise 6
(def asym-body-parts [{:name "head" :size 3}
                      {:name "eye-1" :size 1}
                      {:name "ear-1" :size 1}
                      {:name "mouth" :size 1}
                      {:name "nose" :size 1}
                      {:name "neck" :size 2}
                      {:name "shoulder-1" :size 3}
                      {:name "upper-arm-1" :size 3}
                      {:name "chest" :size 10}
                      {:name "back" :size 10}
                      {:name "forearm-1" :size 3}
                      {:name "abdomen" :size 6}
                      {:name "kidney-1" :size 1}
                      {:name "hand-1" :size 2}
                      {:name "knee-1" :size 2}
                      {:name "thigh-1" :size 4}
                      {:name "lower-leg-1" :size 3}
                      {:name "achilles-1" :size 1}
                      {:name "foot-1" :size 2}])

(defn matching-parts
  [part n]
  (map (fn [i] (update part :name #(clojure.string/replace % #"-1$" (str "-" i))))
       (range 1 (inc n))))

(defn symmetrize-body-parts
  [body-parts to-add]
  (reduce (fn [acc part] (into acc (set (matching-parts part to-add)))) [] body-parts))
