(ns hospital.colecoes
  (:use [clojure pprint]))

;"Função pop funciona de forma diferente para pilha e fila"

(println "### vetor")
(defn testa-vetor []
  (let [espera [111 222]]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))
(testa-vetor)

(println "### lista")
(defn testa-lista []
  (let [espera '(111 222)]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))))
(testa-lista)

(println "### conjunto")
(defn testa-conjunto []
  (let [espera #{111 222}]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))))
;;(println (pop espera)))) ;;Não rola pop em set
(testa-conjunto)

(println "### fila")
(defn testa-fila []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println (seq espera))
    (println (seq (conj espera 333)))
    (println (seq (conj espera 444)))
    (println (peek espera))
    (println (seq (pop espera)))
    (println (peek espera))
    (pprint espera)))
(testa-fila)