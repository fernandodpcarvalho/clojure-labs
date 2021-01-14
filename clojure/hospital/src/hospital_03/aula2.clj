(ns hospital_03.aula2
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def Paciente
  "Schema de um paciente"
  {:id s/Num, :nome s/Str})

(pprint (s/explain Paciente))
(pprint (s/validate Paciente {:id 15, :nome "Fernando"}))
;(pprint (s/validate Paciente {:id 15, :idade "Fernando"}))
(pprint (s/validate Paciente {:id 15, :nome "Fernando"}))

;Forward compatible = Records que aceitam campos a mais (ex. sistema externo)

;validação na saida
(s/defn novo-paciente :- Paciente
  [id :- s/Num, nome :- s/Str]
  {:id id, :nome nome})
(pprint (novo-paciente 150 "Fernando"))

(defn estritament-positivo? [x]
  (> x 0))
(def EstritamentePositivo (s/pred estritament-positivo? 'estritament-positivo))

(pprint (s/validate EstritamentePositivo 15))
;(pprint (s/validate EstritamentePositivo -1))



;validação na saida
(def Paciente
  "Schema de um paciente"
  ;{:id (s/constrained s/Int estritament-positivo?), :nome s/Str})
  {:id (s/constrained s/Int #(> % 0)), :nome s/Str})
  ;{:id (s/constrained s/Int pos?), :nome s/Str})
(pprint (s/validate Paciente {:id 15, :nome "Fernando"}))
(pprint (s/validate Paciente {:id -15, :nome "Fernando"}))



(def Data
  "A schema for a nested data type"
  {:a {:b s/Str
       :c s/Int}
   :d [{:e s/Keyword
        :f [s/Num]}]})
