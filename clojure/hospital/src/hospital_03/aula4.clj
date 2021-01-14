(ns hospital_03.aula4
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(s/set-fn-validation! true)

(def PosInt (s/pred pos-int? 'inteiro-positivo))
(def Plano [s/Keyword])
(def Paciente
  {:id PosInt, :nome s/Str, :plano Plano, (s/optional-key :nascimento) s/Str})

(pprint (s/validate Paciente {:id 15, :nome "Fernando", :plano [:raio-x]}))
