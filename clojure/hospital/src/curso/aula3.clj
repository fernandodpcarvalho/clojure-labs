(ns curso.aula3)

;PREDICATE
(defn aplica-desconto?
  [valor-bruto]
  (if (> valor-bruto 100)
    true
    false))
(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))
(println (valor-descontado 3000))
(println (valor-descontado 100))

;SEM ELSE
(defn aplica-desconto?
  [valor-bruto]
  (println "chamando a versao sem else")
  (if (> valor-bruto 100)
    true))
(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))

;WHEN = SÓ POSSUI CASO VERDADEIRO
(defn aplica-desconto?
  [valor-bruto]
  (println "chamando a versao com when")
  (when (> valor-bruto 100)
    true))
(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))

;DIRETO = SEM IF OU WHEN
(defn aplica-desconto?
  [valor-bruto]
  (println "chamando a versao direta")
  (> valor-bruto 100))
(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))

;REcebe uma função como função
(defn mais-caro-que-100?
  [valor-bruto]
  (> valor-bruto 100))

;MEsma função sem nome
(fn [valor-bruto] (> valor-bruto 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se deve aplicar desconto*"
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))
(println (valor-descontado mais-caro-que-100? 3000))
(println (valor-descontado mais-caro-que-100? 100))

;Recebendo função sem nome
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 3000))
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 100))

;Recebendo função sem nome
(println (valor-descontado (fn [v] (> v 100)) 3000))
(println (valor-descontado (fn [v] (> v 100)) 100))

;Recebendo função sem nome
(println (valor-descontado #(> % 100) 3000))
(println (valor-descontado #(> % 100) 100))