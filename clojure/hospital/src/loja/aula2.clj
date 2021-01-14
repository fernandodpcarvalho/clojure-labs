(ns loja.aula2)


(def vetorA ["Fernando" "Thais" "Nelson" "Julia"])

(defn conta
  [total-ate-agora elementos]
  (if (next elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta 0 vetorA))
(println (conta 0 []))

(defn conta
  [total-ate-agora elementos]
  (if (seq elementos)
    (recur (inc total-ate-agora) (next elementos))
    total-ate-agora))

(println (conta 0 vetorA))
(println (conta 0 []))

(defn minha-funcao
  ([parametro1] (println "p1" parametro1))
  ([parametro1 parametro2] (println "p2" parametro1 parametro2)))
(minha-funcao "Fernando")
(minha-funcao "Fernando" "Thais")


(defn conta
  ([elementos]
   (conta 0 elementos))

  ([total-ate-agora elementos]
   (if (seq elementos)
     (recur (inc total-ate-agora) (next elementos))
     total-ate-agora)))

(println (conta 0 vetorA))
(println (conta vetorA))
(println (conta []))


(println "Loop similar ao for: (for total-ate-agora 0, elementos-restantes elementos, +1 next)")
(defn conta
  [elementos]
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes)
      ;;recur reatribui os valores às variáveis (incrementa e percorre)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))
(println (conta vetorA))
(println (conta []))

:: recur  = pode fazer recorrencia na própria função (recursividade) ou em outro ponto dentro da função (loop)