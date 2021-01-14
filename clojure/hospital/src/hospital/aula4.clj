(ns hospital.aula4
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))


;Simulação usando mapv para forçar a execução eager.
(defn simula-um-dia-em-paralelo-mapv
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]
    (mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
;(simula-um-dia-em-paralelo-mapv)


;Refatorada
(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        inicia-thread-de-chegada #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))]
    (mapv inicia-thread-de-chegada pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
;(simula-um-dia-em-paralelo)

;Refatorada
;(defn inicia-thread-de-chegada
;  [hospital pessoa]
;  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))
;
;(defn preparada
;  [hospital]
;  (fn [pessoa] (inicia-thread-de-chegada hospital pessoa)))

(defn inicia-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]
        inicia (partial inicia-thread-de-chegada hospital)]
    (mapv inicia pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
;(simula-um-dia-em-paralelo-com-mapv-extraida)

(println "Usando doseq")
(defn simula-um-dia-em-paralelo-com-doseq
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111" "222" "333" "444" "555" "666"]]
    (doseq [pessoa pessoas]
      (inicia-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
;(simula-um-dia-em-paralelo-com-doseq)

(println "Usando dotimes")
(defn simula-um-dia-em-paralelo-com-dotimes
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (dotimes [pessoa 6]
      (inicia-thread-de-chegada hospital pessoa))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))
(simula-um-dia-em-paralelo-com-dotimes)