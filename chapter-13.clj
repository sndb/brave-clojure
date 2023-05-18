;; Exercise 1
(defmulti full-moon-behavior (fn [were-creature] (:were-type were-creature)))

(defmethod full-moon-behavior :wolf
  [were-creature]
  (str (:name were-creature) " will howl and murder"))

(defmethod full-moon-behavior :simmons
  [were-creature]
  (str (:name were-creature) " will encourage people and sweat to the oldies"))

(defmethod full-moon-behavior nil
  [were-creature]
  (str (:name were-creature) " will stay at home and eat ice cream"))

(defmethod full-moon-behavior :default
  [were-creature]
  (str (:name were-creature) " will stay up all night fantasy footballing"))

(defmethod full-moon-behavior :cat
  [were-creature]
  (str (:name were-creature) " will meow and eat fish"))

;; Exercise 2
(defprotocol WereCreature
  (full-moon-behavior [x]))

(defrecord WereSimmons [name]
  WereCreature
  (full-moon-behavior [x]
    (str name " will encourage people and sweat to the oldies")))

;; Exercise 3
(defprotocol Decorator
  (decorate [x]))

(extend-type java.lang.Long
  Decorator
  (decorate [x]
    (str "-*-" x "-*-")))

(extend-protocol Decorator
  java.lang.String
  (decorate [x]
    (str "[[[" x "]]]"))

  java.lang.Double
  (decorate [x]
    (str "<-" x "->"))

  java.lang.Object
  (decorate [x]
    (str "+" x "+")))

;; Exercise 4
(defmulti attack :class)

(defmethod attack :mage
  [x y]
  (str (:name x) " throws a fireball at " (:name y)))

(defmethod attack :warrior
  [x y]
  (str (:name x) " hits " (:name y) " with a sword"))

(defmethod attack :hunter
  [x y]
  (str (:name x) " fires an arrow at " (:name y)))
