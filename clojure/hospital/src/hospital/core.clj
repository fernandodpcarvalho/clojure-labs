(ns hospital.core
  (:use [clojure pprint])
  (:require [hospital.model :as h.model]))

;Fila de espera geral
;Tres filas de atendimento:
;laboratorio1
;laboratorio2
;laboratorio3

(let [hospital (h.model/novo-hospital)]
  (pprint hospital))

(pprint h.model/fila-vazia)
