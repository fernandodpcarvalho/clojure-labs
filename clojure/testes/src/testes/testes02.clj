(ns testes.testes02)

;Quote:
;' = indicate that the next form should be read but not evaluated.
'(+ 1 2)
;=> (+ 1 2)

;Syntax-quote: ` ~ ~@
;    ` = Return the symbol’s full namespace:
`(+ 1 2)
;=> (clojure.core/+ 1 2)

;~ = Unquote
`(+ 1 (inc 1))
;=> (clojure.core/+ 1 (clojure.core/inc 1))

`(+ 1 ~(inc 1))
;=> (clojure.core/+ 1 2)

`(+ ~(list 1 2 3))
`(+ ~'(1 2 3))

;:: is used to auto-resolve a keyword in the current namespace.
;~@ = "unquote splicing" = transforma uma coleção de elementos em argumentos
`(+ ~@(list 1 2 3))
`(+ ~@'(1 2 3))
;=> (clojure.core/+ 1 2 3)


(defmacro code-critic
  "Phrases are courtesy Hermes Conrad from Futurama"
  [bad good]
  `(do (println "Great squid of Madrid, this is bad code:"
                (quote ~bad))
       (println "Sweet gorilla of Manila, this is good code:"
                (quote ~good))))

(code-critic (1 + 1) (+ 1 1))

;;;
(defn criticize-code
  [criticism code]
  `(println ~criticism (quote ~code)))

(defmacro code-critic
  [bad good]
  `(do ~(criticize-code "Cursed bacteria of Liberia, this is bad code:" bad)
       ~(criticize-code "Sweet sacred boa of Western and Eastern Samoa, this is good code:" good)))

;(code-critic (1 + 1) (+ 1 1))

(defmacro code-critic
  [{:keys [good bad]}]
  `(do ~@(map #(apply criticize-code %)
              [["Sweet lion of Zion, this is bad code:" bad]
               ["Great cow of Moscow, this is good code:" good]])))

;(code-critic (1 + 1) (+ 1 1))

;gensym function produces unique symbols on each successive call:
;to introduce let bindings in your macro, you can use a gensym.
(gensym 'message)
(gensym)

(def message "Good job!")
(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    `(let [~macro-message "Oh, big deal!"]  ;the name of the simble is the value of ~macro-message = (gensym 'message)
       ~@stuff-to-do
       (println "I still need to say: " ~macro-message))))

(without-mischief
  (println "Here's how I feel about that thing you did: " message))
; => Here's how I feel about that thing you did:  Good job!
; => I still need to say:  Oh, big deal!


(defmacro without-mischief
  [& stuff-to-do]
  (let [macro-message (gensym 'message)]
    (println macro-message)
    `(let [~macro-message "Oh, big deal!"]
       ~@stuff-to-do
       ;(println "I still need to say: " ~macro-message)
       )))
(without-mischief
  (println "Here's how I feel about that thing you did: " message))

;auto-gensym
`(let [name# "Larry Potter"] name#)

;double evaluation: argument gets evaluated more than once.
;Solução: evaluar o argumento uma vez e armazenar em um símbolo para usar posteriormente.


(def mapa {::a "a" ::b "b"})
(clojure.pprint/pprint mapa)
