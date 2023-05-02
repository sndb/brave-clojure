;; Exercise 1
(defn brave
  [term]
  (str "https://search.brave.com/search?q=" term))

(defn bing
  [term]
  (str "https://www.bing.com/search?q=" term))

(defn search
  [term]
  (let [page (promise)]
    (doseq [engine [brave bing]]
      (future (deliver page (slurp (engine term)))))
    (deref page 5000 "timed out")))

;; Exercise 2
(defn search-with
  [term engines]
  (let [page (promise)]
    (doseq [engine engines]
      (future (deliver page (slurp (engine term)))))
    (deref page 5000 "timed out")))

;; Exercise 3
(defn collect-urls
  [content]
  (map second (re-seq #"<a href=\"(https?://.+?)\"" content)))

(defn search-urls
  [term engines]
  (->> engines
       (map #(future (slurp (% term))))
       (map #(collect-urls (deref % 5000 "")))
       (apply concat)))
