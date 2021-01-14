(ns hospital-04.logic)

(defn cabe-na-fila?
  [hospital departamento]
  (-> hospital
      departamento
      count
      (< 5)))

;(defn cabe-na-fila-02?
;  [hospital departamento]
;  (when-let [fila (get hospital departamento)]
;    (-> fila
;        count
;        (< 5))))
;
;;some-> se alguém for nulo devolve nulo
;(defn cabe-na-fila-03?
;  [hospital departamento]
;  ;(count (get hospital departamento))
;  (some-> hospital
;          departamento
;          count
;          (< 5)))
