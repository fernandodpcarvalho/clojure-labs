(ns curso.aula6)

(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})

(defn imprime-e-15 [valor]
  (println "valor" (class valor) valor)
  15)
(println (map imprime-e-15 pedido))

;(defn imprime-e-15 [valor]
;  (println "valor" (class valor) valor)
;  15)
;(println (map imprime-e-15 pedido))

;Desestruturar um map
(defn imprime-e-15  [[chave valor]]
  (println chave ":" valor)
  15)
(println (map imprime-e-15 pedido))

(println "--------------")
;Se um parâmetro não for usado, substitui por "_"
(defn preco-total-por-produto [[_ valor]]
  (* (:quantidade valor) (:preco valor)))
(println (map preco-total-por-produto pedido))
(println (reduce + (map preco-total-por-produto pedido)))

(println "--------------")
(defn total-do-pedido [pedido]
  (reduce + (map preco-total-por-produto pedido)))
(println (total-do-pedido pedido))

(println "--------------THREAD LAST")
(defn total-do-pedido
  [pedido]
  (->> pedido
      (map preco-total-por-produto ,,,)
      (reduce + ,,,)))
(println (total-do-pedido pedido))


(println "--------------THREAD LAST recebe só produto")
(defn preco-total-do-produto [produto]
  (* (:quantidade produto) (:preco produto)))
(defn total-do-pedido
  [pedido]
  (->> pedido
       vals
       (map preco-total-do-produto ,,,)
       (reduce + ,,,)))
(println (total-do-pedido pedido))


(println "---Filter")
(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}
             :chaveiro {:quantidade 1}})
(defn gratuito?
  [[chave item]]
  (<= (get item :preco 0) 0))
(println (filter gratuito? pedido))

(println "---Filter com lambida")
(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))
(println (filter (fn [[chave item]] (gratuito? item)) pedido))
(println (filter #(gratuito? (second %)) pedido))

(println "---Pago - Filter com lambida")
(defn pago?
  [item]
  (> (get item :preco 0) 0))
(println (filter #(pago? (second %)) pedido))

(defn pago?
  [item]
  (not (gratuito? item)))
(println (filter #(pago? (second %)) pedido))

;Definindo um simbolo que aponta para uma composição de funcões com gratuito?
;comp retorna uma função, composicao de duas.
(def pago? (comp not gratuito?))
(println (filter #(pago? (second %)) pedido))


(println "Exercício: Como calcular o total de certificados de todos os clientes?" )
(def clientes [
               { :nome "Guilherme"
                :certificados ["Clojure" "Java" "Machine Learning"] }
               { :nome "Paulo"
                :certificados ["Java" "Ciência da Computação"] }
               { :nome "Daniela"
                :certificados ["Arquitetura" "Gastronomia"] }])

(defn total-certificados
  [clientes]
  (count (get clientes :certificados)))
(println (map total-certificados clientes))
(println (reduce + (map total-certificados clientes)))
(println (reduce * (map total-certificados clientes)))

(println (reduce + (map (fn [clientes] (count (get clientes :certificados))) clientes)))

(defn total-certificados
  [clientes]
  (->> clientes
       (map :certificados)
       (map count)
       (reduce +)))
(println (total-certificados clientes))

;(->> clientes (map :certificados) (map count) (reduce +))