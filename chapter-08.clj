(def order-details
  {:name "Mitchard Blimmons"
   :email "mitchard.blimmons@gmail.com"})

(def order-details-validations
  {:name
   ["Please enter a name" not-empty]

   :email
   ["Please enter an email address" not-empty

    "Your email address doesn't look like an email address"
    #(or (empty? %) (re-seq #"@" %))]})

(defn error-messages-for
  "Return a seq of error messages"
  [to-validate message-validator-pairs]
  (map first (filter #(not ((second %) to-validate))
                     (partition 2 message-validator-pairs))))

(defn validate
  "Returns a map with a vector of errors for each key"
  [to-validate validations]
  (reduce (fn [errors validation]
            (let [[fieldname validation-check-groups] validation
                  value (get to-validate fieldname)
                  error-messages (error-messages-for value validation-check-groups)]
              (if (empty? error-messages)
                errors
                (assoc errors fieldname error-messages))))
          {}
          validations))

(defmacro if-valid
  "Handle validation more concisely"
  [to-validate validations errors-name & then-else]
  `(let [~errors-name (validate ~to-validate ~validations)]
     (if (empty? ~errors-name)
       ~@then-else)))

;; Exercise 1
(defmacro when-valid
  [to-validate validations & then]
  `(let [errors# (validate ~to-validate ~validations)]
     (when (empty? errors#)
       ~@then)))

(when-valid order-details order-details-validations
            (println "It's a success!")
            :success)

;; Exercise 2
(defmacro my-or
  ([] nil)
  ([x] x)
  ([x & next]
   `(if-let [result# ~x]
      result#
      (my-or ~@next))))

;; Exercise 3
(defn attr
  [attribute]
  (fn [character]
    (get-in character [:attributes attribute])))

(defmacro defattrs
  [& args]
  (let [pairs (partition 2 args)]
    (cons 'do (map (fn [[name attribute]] `(def ~name (attr ~attribute))) pairs))))

(defattrs
  c-int :intelligence
  c-str :strength
  c-dex :dexterity)
