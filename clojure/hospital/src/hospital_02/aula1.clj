(ns hospital-02.aula1
  (:use [clojure pprint]))

(defn adiciona-paciente
  "Os pacientes são um mapa da seguinte forma { 15 {paciente 15}, 23 {paciente 23} }.
  O paciente { :id 15 ... }"
  [pacientes paciente]
  (let [id (:id paciente)]
    (assoc pacientes id paciente)))

;(defn testa-uso-de-pacientes []
;  (let [pacientes {}
;        guilherme {:id 15, :nome "Guilherme", :nascimento "18/9/1981"}]
;    (pprint (adiciona-paciente pacientes guilherme))))
;
;(testa-uso-de-pacientes)

(defn testa-uso-de-pacientes []
  (let [pacientes {}
        guilherme {:id 15 :nome "Guilherme" :nascimento "18/9/1981"}
        daniela {:id 20 :nome "Daniela" :nascimento "18/9/1982"}
        paulo {:nome "Paulo", :nascimento "18/10/1983"}]
    (pprint (adiciona-paciente pacientes guilherme))
    (pprint (adiciona-paciente pacientes daniela))
    (pprint (adiciona-paciente pacientes paulo))
    ))

;(testa-uso-de-pacientes)

;Paciente como uma classe Java. Para trabalhar com interoperabilidade com Java, com orientação a objetos.
(defrecord Paciente [id nome nascimento])

(println (->Paciente 15 "Fernando" "17/02/1984"))
(pprint (->Paciente 15 "Fernando" "17/02/1984"))

;Construtor: tem que criar com os parametros definidos.
(pprint (Paciente. 15 "Fernando" "17/02/1984"))
(pprint (Paciente. "Fernando" 15 "17/02/1984"))

;map: permite valores a menos ou a mais
(pprint (map->Paciente {:id 15, :nome "Fernando", :nascimento "17/02/1984"}))

(pprint (Paciente. 15 "18/9/1981" "Guilherme"))
(pprint (map->Paciente {:id 15, :nome "Guilherme", :nascimento "18/9/1981"}))

(pprint (assoc (Paciente. nil "Guilherme" "18/9/1981") :id 38))
(pprint (class (assoc (Paciente. nil "Guilherme" "18/9/1981") :id 38)))

(pprint (= (->Paciente 15 "Fernando" "17/02/1984") (->Paciente 15 "Fernando" "17/02/1984")))
(pprint (= (->Paciente 15 "Fernando" "17/02/1984") (->Paciente 20 "Fernando" "17/02/1984")))

(let [fernando (->Paciente 15 "Fernando" "17/02/1984")]
  (println (:id fernando))
  (println (vals fernando))
  (println (class fernando))
  (println (record? fernando))                              ;;Classe Java?
  (println (.nome fernando))
  )