(ns curso.aula5)

;hashmap
(def estoque {"Mochila" 10 "Camiseta" 5})
(def estoque {"Mochila" 10, "Camiseta" 5})
(def estoque {"Mochila"  10,
              "Camiseta" 5})

(println estoque)
(println "Temos" (count estoque) "elementos")
(println "Chaves" (keys estoque))
(println "Valores" (vals estoque))

;keyword
;:mochila
(def estoque {:mochila  10,
              :camiseta 5})

(println (assoc estoque :cadeira 3))
(println "estoque" estoque)

(println "estoque update" (update estoque :mochila inc))

(defn tira-um
  [valor]
  (- valor 1))
(println "tira-um" (update estoque :mochila tira-um))
(println "tira-tres-lambida" (update estoque :mochila #(- % 3)))
(println (dissoc estoque :camiseta))

(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})
(println "--------------------")
(println pedido)
(def pedido (assoc pedido :chaveiro {:quantidade 1, :preco 10}))
(println pedido)
(println (pedido :mochila))
(println (get pedido :mochila))
(println (get pedido :cadeira))
(println (get pedido :cadeira {}))
(println (:mochila pedido))
(println (:cadeira pedido))
;Especificar o tipo de retorno caso não encontre o elemento
;Evita o nullpointerexception
;Deixa o "get" implícito
(println (:cadeira pedido {}))

(println (:quantidade (:mochila pedido)))

(println "update-in" (update-in pedido [:mochila :quantidade] inc))

;THREADING
;pedido.get (mochila).get (quantidade)
;Este mérodo de encadeamento lembra mais o método tradicional em POO
;cada chamada passa o resultado pra próxima
;O excesso de parenteses dificulta a legibilidade
(println (-> pedido
             :mochila
             :quantidade))
(-> pedido
    :mochila
    :quantidade
    println)