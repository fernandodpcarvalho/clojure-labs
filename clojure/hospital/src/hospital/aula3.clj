(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

;variavel global cessível por qualquer thread
(def nome "Fernando")

(println "Atomo é um encapsulamento para uma estrutura, e esta não pode ser modificada, apenas retirada por inteira")
(defn testa-atomo []
  (let [hospital (atom {:espera h.model/fila-vazia})]
    (println hospital)
    (pprint hospital)
    ;retira conteúdo do atomo
    (pprint (deref hospital))
    (pprint @hospital)

    ;forma errada de alterar conteudo dentro do atomo
    (pprint (assoc @hospital :laboratorio1 h.model/fila-vazia))
    (pprint @hospital)

    ;Para alterar deve-se usar uma função que faça isso.
    (swap! hospital assoc :laboratorio1 h.model/fila-vazia)
    (pprint @hospital)

    (swap! hospital assoc :laboratorio2 h.model/fila-vazia)
    (pprint @hospital)

    ;update tradicional imutavel sem efeito
    (update @hospital :laboratorio1 conj "111")
    (pprint @hospital)

    ;com swap
    (swap! hospital update :laboratorio1 conj "111")
    (pprint @hospital)
    ))
;(testa-atomo)
;Funções com exclamação causam efeitos colaterais. Ex: swap!


(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
;(simula-um-dia-em-paralelo)


(defn chega-em-malvado-logando! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir" pessoa))


(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado-logando! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado-logando! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado-logando! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado-logando! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado-logando! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado-logando! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
(simula-um-dia-em-paralelo)