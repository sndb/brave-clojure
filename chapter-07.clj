;; Exercise 1
(eval (read-string "(list 'Daniil \"2001: A Space Odyssey\")"))

;; Exercise 2
(defn infix
  [[x1 plus x2 times x3 minus x4]]
  (list minus
        (list plus
              x1
              (list times
                    x2
                    x3))
        x4))

(infix '(1 + 3 * 4 - 5))
