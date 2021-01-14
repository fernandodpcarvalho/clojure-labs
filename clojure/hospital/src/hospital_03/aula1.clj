(ns hospital_03.aula1
  (:use clojure.pprint)
  (:require [schema.core :as s]))



(pprint (s/validate Long 15))

(s/defn teste-simples [x] (println x))
(teste-simples 30)
(teste-simples "Fernando")

(s/set-fn-validation! true)
(s/defn teste-simples [x :- Long, s :- s/Str] (println x "-" s))
(teste-simples 15 "Fernando")
(teste-simples "15" 20)

;Validações podem ser ativadas em desenvolvimento e testes com boa qualidade.
;E desativadas em produção, exceto nas bordas (camadas de entrada de dados externos)


