(ns curso.aula4)

(def precos [30 700 1000])

(println (precos 0))
(println (get precos 0))

;IndexOutOfBoundsException
;(println (precos 3))

;get trata a IndexOutOfBoundsException
(println "valor padrao nil" (get precos 3))
(println "valor padrao 0" (get precos 3 0))

;o vetor não é alterado. Somente neste impressao
(println (conj precos 5))
(println precos)

(println (update precos 0 inc))

(defn soma-1
  [valor]
  (println "Somando 1 em " valor)
  (+ valor 1))
(println (update precos 0 soma-1))

;código aula anterior
(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

;Loop implicito (f(x) preco(x)
(println "---")
(println "map" (map valor-descontado precos))

;filtra os pares
(println "filter" (filter even? (range 10)))
(println "filter" (filter aplica-desconto? precos))
(println "map após filter" (map valor-descontado (filter aplica-desconto? precos)))

(println "reduce" (reduce + precos))

(defn minha-soma
  [valor1 valor2]
  (println "somando" valor1 valor2)
  (+ valor1 valor2))

(println "reduce" (reduce minha-soma precos ))
(println "reduce" (reduce minha-soma 0 precos ))
(println "reduce" (reduce minha-soma (range 10 )))