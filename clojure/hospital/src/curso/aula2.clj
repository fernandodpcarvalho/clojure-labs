(ns curso.aula2)

(println "Teste função")

(def total-de-produtos 15)
(println total-de-produtos)

(println (+ 13 3))

(def total-de-produtos (+ total-de-produtos 3))
(println total-de-produtos)

(def estoque ["Fernando", "Thais"])
(println (estoque 0))
(println (count estoque))

(defn imprime-mensagem [] (println "------------------") (println "Teste criar função"))
(imprime-mensagem)

(defn valor-descontado
  "Retorna o valor descontado que é 90% do valor bruto"
  [valor-bruto]
  (let [desconto 0.10]
    (* valor-bruto (- 1 desconto))))
(println (valor-descontado 3000))

(println)(valor-descontado 200)

(if (< 500 100)
  (println "maior")
  (println "menor"))

(defn valor-descontado
  "Retorna o valor descontado que é 90% do valor bruto"
  [valor-bruto]
  (let [desconto 0.10]
    (* valor-bruto (- 1 desconto))))
(println (valor-descontado 3000))


(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (println "Calculando desconto de" desconto)
      (- valor-bruto desconto))
    valor-bruto))
(println (valor-descontado 3000))