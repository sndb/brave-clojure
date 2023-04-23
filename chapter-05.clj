;; Exercise 1
(def character
  {:name "Smooches McCutes"
   :attributes {:intelligence 10
                :strength 4
                :dexterity 5}})

(defn attr
  [attribute]
  (fn [character]
    (get-in character [:attributes attribute])))

(def c-int (attr :intelligence))
(def c-str (attr :strength))
(def c-dex (attr :dexterity))

;; Exercise 2
(defn two-comp
  [f g]
  (fn [& args]
    (f (apply g args))))

(defn my-comp
  [& fs]
  (reduce two-comp fs))

;; Exercise 3
(defn my-assoc-in
  [m [k & ks] v]
  (if (empty? ks)
    (assoc m k v)
    (assoc m k (my-assoc-in (get m k) ks v))))

;; Exercise 4
(update-in {:a 1 :b {:c 2 :d {:e 4}} :f 5}
           [:b :d :e]
           (fn [x y] (* x y))
           3)

;; Exercise 5
(defn my-update-in
  [m ks f & args]
  (assoc-in m ks (apply f (get-in m ks) args)))
