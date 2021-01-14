(ns testes.testes)

;;Variadic functions = variable number of parameters. Must occur at the end of the parameter list.
(defn hello [greeting & who]
  (println greeting who))
(hello "Hello" "world" "class")





(def mapa {:campoa "aaa" :campob "bbb" :campoc "ccc"})

(println mapa)
(println (:campoa mapa))

(defn imprime-mapa [mapa]
  (let [campoa (:campoa mapa)
        campob (:campob mapa)
        campoc (:campoc mapa)
        campos (str campoa "-" campob "-" campoc)]
    (println "imprime-mapa" campos)
    campos))
(println "Fora " (imprime-mapa mapa))
;(imprime-mapa mapa)

(defn sum-even-numbers [nums]
  (if-let [nums (seq (filter even? nums))]
    (reduce + nums)
    "No even numbers found."))
(println (sum-even-numbers [1 3 5 7 9]))
(println (sum-even-numbers [1 3 5 7 9 10 12]))

(println "--- SWAP! ---")
;; how to: atomic counter
(def counter (atom 0))
(println counter)

(swap! counter inc)
(println counter)

(def fernando (atom {}))
(println fernando)
(println @fernando)

(swap! fernando assoc :gosta "carne")
(println @fernando)
(println (:gosta @fernando))

(def thais (atom {:gosta "folhas"}))
(println @thais)
(swap! thais assoc :gosta "folhas)")

(swap! fernando assoc :gosta-tambem "cerveja")
(swap! fernando conj {:nao-gosta "cafe-frio"})
;só consegue alterar tudo dentro do atomo


(println "get-in")
(def m {:username "sally"
        :profile  {:name    "Sally Clojurian"
                   :address {:city "Austin" :state "TX"}}})
(println (get m :username))
(println (get-in m [:profile :name]))
(println (get-in m [:profile :address :city]))
(println (get-in m [:profile :address :zip-code]))
(println (get-in m [:profile :address :zip-code] "no zip code!"))

(println "\n\nThreads")
;https://clojure.org/guides/threading_macros
;"->" "->>" "as->" Sends the result of a function call to the next function call")
;thread-first ("->"): Insert the threaded value as the first argument of the next function.
;thread-last ("->>"): Insert the threaded value as the last argument of the next function.
;thread-??? (as->): For function calls with varying insertion points.
;commas mark the place where the argument will be inserted
;OBS: A bare symbol or keyword without parentheses is interpreted as a simple function invocation with a single argument
(println "thread-first")
(println (-> 2 (+ 3)))
(def a 5)
(-> a
    (+ 3)
    (+ 6)
    println)
(-> a
    (+ 3)
    (= 6)
    println)
(-> a
    (+ 3)
    (/ ,,, 4)
    println)

(def person {:name "Socrates", :age 39})

(println person)
(defn transform [person]
  (update (assoc person :hair-color :gray) :age inc))
(println (transform person))

(println person)
(defn transform* [person]
  (-> person
      (assoc :hair-color :gray)
      (update :age inc)))
(println (transform* person))

(println person)
(defn transform** [person]
  (-> person
      (assoc ,,, :hair-color :gray)
      (update ,,, :age inc)))
(println (transform** person))

;; It can also be useful for pulling values out of deeply-nested
;; data structures:
(def person-nested
         {:name "Mark Volkmann"
          :address {:street "644 Glen Summit"
                    :city "St. Charles"
                    :state "Missouri"
                    :zip 63304}
          :employer {:name "Object Computing, Inc."
                     :address {:street "12140 Woodcrest Dr."
                               :city "Creve Coeur"
                               :state "Missouri"
                               :zip 63141}}})
(println (-> person-nested :employer :address :city))
(println (:city (:address (:employer person-nested))))

(-> "a b c d"
    .toUpperCase
    (.replace "A" "X")
    (.split " ")
    first
    )

(println "thread-last")
(-> a
    (+ 3)
    (/ ,,, 4)
    println)
(->> a
    (+ 3)
    (/ 4 ,,,)
    println)

(println "thread as->")
;;https://clojuredocs.org/clojure.core/as-%3E
;https://clojuredocs.org/clojure.core/as-%3E


;By convention, core functions that operate on sequences expect the sequence as their last argument. Accordingly, pipelines containing map, filter, remove, reduce, into, etc usually call for the ->> macro.
;Core functions that operate on data structures, on the other hand, expect the value they work on as their first argument. These include assoc, update, dissoc, get and their -in variants. Pipelines that transform maps using these functions often require the -> macro.
;When calling methods through Java interop, the Java object is passed in as the first argument. In such cases, -> is useful, for example, to check a string for a prefix:


;conj
;;conjoining to a vector is done at the end
(println (conj [1 2 3] 4))
;;conjoining to a list is done at the beginning
(println (conj '(1 2 3) 4))
;;conjoining multiple items is done in order
(println (conj [1 2] 3 4))
(println (conj '(1 2) 3 4))
;contrário de conj
(disj #{1 2 3} 2)
;#{1 3}
;

(println (conj {:firstname "John" :lastname "Doe"} {:age 25 :nationality "Chinese"}))


;;update

(def p {:name "James" :age 26})
(println p)
(println (update p :age inc))
(println p)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def transacoes
  [{:valor 33.0 :tipo "despesa" :comentario "Almoço"
    :moeda "R$" :data "19/11/2016"}
   {:valor 2700.0 :tipo "receita" :comentario "Bico"
    :moeda "R$" :data "01/12/2016"}
   {:valor 29.0 :tipo "despesa" :comentario "Livro de Clojure"
    :moeda "R$" :data "03/12/2016"}])

(defn valor-sinalizado [transacao]
  (let [moeda (:moeda transacao "R$")
        valor (:valor transacao)]
    (if (= (:tipo transacao) "despesa")
      (str moeda " -" valor)
      (str moeda " +" valor))))
(valor-sinalizado (first transacoes))
;; "R$ -33.0"
(valor-sinalizado (second transacoes))
;; "R$ +2700.0"
(valor-sinalizado {:valor 9.0})
;; "R$ +9.0"

(defn data-valor [transacao]
  (str (:data transacao) " => " (valor-sinalizado transacao)))
(data-valor (first transacoes))
;; "19/11/2016 => R$ -33.0"

;;;;;;;Destructing
(def my-line [[5 10] [10 20]])

(let [p1 (first my-line)
      p2 (second my-line)
      x1 (first p1)
      y1 (second p1)
      x2 (first p2)
      y2 (second p2)]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
;= "Line from ( 5 , 10 ) to ( 10 , 20 )"

;= Using the same vector as above
(let [[p1 p2] my-line
      [x1 y1] p1
      [x2 y2] p2]
  (println "Line from (" x1 "," y1 ") to (" x2 ", " y2 ")"))
;= "Line from ( 5 , 10 ) to ( 10 , 20 )"

(reduce + [1 2 3 4 5])  ;;=> 15

;;;;;
(defn transform
  [coll]
  (reduce (fn [ncoll [k v]]
            (assoc ncoll k (* 10 v)))
          {}
          coll))

(transform {:a 1 :b 2 :c 3})
;;{:a 10 :b 20 :c 30}


;;;;;;;assoc
(assoc {} :key1 "value" :key2 "another value")
;;=> {:key2 "another value", :key1 "value"}

;; Here we see an overwrite by a second entry with the same key
(assoc {:key1 "old value1" :key2 "value2"}
  :key1 "value1" :key3 "value3")
;;=> {:key3 "value3", :key2 "value2", :key1 "value1"}

;; We see a nil being treated as an empty map
(assoc nil :key1 4)
;;=> {:key1 4}

;; 'assoc' can be used on a vector (but not a list), in this way:
;; (assoc vec index replacement)
(assoc [1 2 3] 0 10)     ;;=> [10 2 3]

(seq '(1))
;;=> (1)
(seq [1 2])
;;=> (1 2)
(seq "abc")
;;=> (\a \b \c)

;apply
(max 0 1 2) ; => 2
(max [0 1 2]) ; => [0 1 2]
(apply max [1 2 3]) ; => 3

;Partial
;"Fixa" o valor de algum argumento
;Recebe uma função "f" e um argumento "a". Retorna a função "f" fixando um dos seus argumentos com valor "a"
(def add10 (partial + 10))
(add10 3) ; => 13
(add10 5) ; => 15

(def complement-even? (complement even?)) ;(complement-even? 2)  => false

;memorize
(def memoize-test (memoize (fn [x] (Thread/sleep 2000) x)))
(time (memoize-test "Fernando"))
(time (memoize-test "Thais"))


;interact with a namespace’s map of symbols-to-interned-vars
(ns-interns *ns*)