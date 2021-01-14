(ns loja.aula5
  (:require
    [loja.db :as l.db]
    [loja.logic :as l.logic]))

(defn gastou-bastante? [info-do-usuario]
  (> (:preco-total info-do-usuario) 500))

(println "Keep = map + filter")
(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))
  (println "filter" (filter gastou-bastante? resumo)))

(println "----------------------------")

(defn gastou-bastante? [info-do-usuario]
  (println "gastou-bastante?" (:usuario-id info-do-usuario))
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]
  (println "keep" (keep gastou-bastante? resumo))
  (println "filter" (filter gastou-bastante? resumo)))

(println "Comportamento lazy = não guloso.")
(println "Vai gerar tudo" (range 10))
(println "Vai gerar somente o necessário" (take 2 (range 10)))

(let [sequencia (range 100)]
  (println (take 10 sequencia))
  (println (take 2 sequencia))
  (println sequencia))

(defn filtro1 [x]
  (println "filtro1" x)
  x)
(println (map filtro1 (range 10)))

(defn filtro2 [x]
  (println "filtro2" x)
  x)
(println (map filtro2 (map filtro1 (range 10))))

(println "Map: até 32 ele é eager")
(->> (range 10)
     (map filtro1)
     (map filtro2)
     println)

(println "Map: acima de 32 ele é laze. (Chunk = 32)")
(->> (range 50)
     (map filtro1)
     (map filtro2)
     println)

(println "Mapv: força ser eager)")
(->> (range 50)
     (mapv filtro1)
     (mapv filtro2)
     println)


(println "Map com lista ligada é 100% lazy (chunk = 1)")
(->> '(0 1 2 3 4 5 6 7 8 9)
     (map filtro1)
     (map filtro2)
     println)

;Funções com entredas muito grandes ou que tendem a infinito (ex: range) a execução é lazy
;PRa coisas grandes faz diferença pensar se é melhor eager ou lazy