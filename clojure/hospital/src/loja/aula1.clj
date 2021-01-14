(ns loja.aula1)

(def vetorA ["Fernando" "Thais" "Nelson" "Julia"])
(println vetorA)

(def mapaA {"Fernando" 36, "Thais" 31})
(println mapaA)

(def listaLigada '(1 2 3 4 5))
(println listaLigada)

(map println vetorA)

(println (first vetorA))
(println (rest vetorA))
(println (next vetorA))

(defn meu-mapa
  [funcao sequencia]
  (let [primeiro (first sequencia)]
    (if (not (nil? primeiro))
      (do
        (funcao primeiro)
        (meu-mapa funcao (rest sequencia))))))
(meu-mapa println vetorA)
(meu-mapa println ["Fernando" false "Nelson" "Julia"])
(meu-mapa println [])
(meu-mapa println nil)

;recur troca a recursão por laço
;Tail recursion = O recur deve vir na ultima instrução, que retorna.
;(defn meu-mapa
;  [funcao sequencia]
;  (let [primeiro (first sequencia)]
;    (if (not (nil? primeiro))
;      (do
;        (funcao primeiro)
;        (recur funcao (rest sequencia))))))
;(meu-mapa println (range 5000))

(println (= 2 "2"))