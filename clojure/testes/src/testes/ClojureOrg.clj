(ns testes.ClojureOrg)

;https://clojure.org/guides/learn/

;Symbols:
;clojure.core/+  ; namespaced symbol
;:alpha          ; keyword
;:release/alpha  ; keyword with namespace

;Sequential Collections
(def clj-list '(1 2 3))                                         ;;Not indexed
(def clj-vector [1 2 3])                                        ;;Indexed
;Hashed Collections
(def clj-set #{1 2 3})                                          ;;unordered and with no duplicates.
(def clj-map {:a 1, :b 2 "c" 3})

;sorted sets
(conj (sorted-set) "Bravo" "Charlie" "Sigma" "Alpha")
(conj (sorted-set) 4 2 3 1)

;sorted maps
(sorted-map "Bravo" 204 "Alfa" 35 "Sigma" 99 "Charlie" 100)

;;adding elements
(conj clj-list 4)                                               ;;insere no início
(conj clj-vector 4)                                             ;;insere no final
(conj clj-set 4)
(conj clj-map {:d 4})

(assoc clj-map "e" 5)
(dissoc clj-map "c" 3)
(keys clj-map)
(vals clj-map)

(zipmap [:a :b :c :d :e] [1 2 3 4 5])                       ;;make a map

(def clj-map2 {:y 22})
(merge clj-map clj-map2)

;Records: Alternative to maps. Better performance.
;Also has a named "type" which can be used for polymorphic behavior
(defrecord Person [first-name last-name age occupation])
(Person. "Thais" "Teles" "31" "BI")
(->Person "Fernando" "Carvalho" "36" "Dev")


;into: putting one collection into another.
(def players #{"Alice" "Bob" "Kelly"})
(def new-players ["Tim" "Sue" "Greg"])
(into players new-players)
#{"Alice" "Greg" "Sue" "Bob" "Tim" "Kelly"}

;;;Evaluate:
;Most literal Clojure forms evaluate to themselves, except symbols and lists:
;Symbols are used to refer to something else and when evaluated, return what they refer to.
;Lists are evaluated as invocation.
(+ 3 4)
;3 and 4 evaluate to themselves (longs)
;+ evaluates to a function that implements +
;evaluating the list will invoke the + function with 3 and 4 as arguments

;Suspend evaluation:
;For symbols and lists.
;When a symbol should just be a symbol without looking up what it refers to:
(+ 3 4)
;=> 7
'(+ 3 4)
;=> (+ 3 4)

(eval '(+ 1 1))


;Functions:
;Multi-arity functions:
(defn messenger
  ([] (messenger "Hello world!"))
  ([msg] (println msg)))
(messenger)
(messenger "Porra")


;Variadic functions:
;variable number of parameters.
;Must occur at the end of the parameter list.
;The "variable number" parameter becames a list inside the function.
(defn hello [greeting & who]
  (println greeting who))
(hello "Hello" "world" "class")

(defn mais [n & ns]
  (apply + n ns))
(mais 1 2 3 4 5 6)


;Applying Functions: Invokes a function to the argments (one + a list)
(apply + '(1 2 3))
(apply + 1 2 '(3 4))

;Anonymous Functions
(def mais (fn [x y] (+ x y)))
(mais 1 2)

(def menos #(- %1 %2))
(menos 5 3)

(def imprime #(println %1 %&))
(imprime "Fernando" "de" "Paula" "Carvalho")


;Closure: Function that has access to some named value/variable outside its own scope
(defn messenger-builder [greeting]
  (fn [who] (println greeting who)))                        ; closes over greeting
;; greeting provided here, then goes out of scope
(def hello-er (messenger-builder "Hello"))
;; greeting value still available because hello-er is a closure
(hello-er "world!")
;; Hello world!

(def foo
  (let [counter (atom 0)]
    (fn [] (do (swap! counter inc) @counter))))
(foo)                                                       ;;=> 1
(foo)                                                       ;;=> 2
(foo)                                                       ;;=> 3, etc

;Java Interop
;; make a function to invoke .length on arg
(fn [obj] (.length obj))
;Java methods are not Clojure functions
;Can’t store them or pass them as arguments
;Can wrap them in functions when necessary

;cond is similar to case.

;dotimes: Evaluate expression n times
(dotimes [i 3] (println i))

;doseq: Iterates over a sequence
(doseq [n (range 3)] (println n))

;with-open
;for

;macro characters = specific reading behavior
;Metadata (^):
; Symbols and collections support metadata, a map of data about the symbol or collection.
; It's not considered to be part of the value of an object. As such, metadata does not impact
; equality (or hash codes).
; Two objects that differ only in metadata are equal.
; An object with different metadata is a different object.
;(meta #'function) = return the metadata
(defn
  ^{:name   "mais",
    :static true,
    :doc    "Similar to +"}
  mais [n & ns]
  (apply + n ns))
(mais 1 2 3 4 5 6)

(meta #'mais)

;https://clojure.org/guides/weird_characters

;with-meta: (function) Usage: (with-meta obj m)
;Returns an object of the same type and value as obj, with map m as its metadata.
(meta (with-meta mais {:bye true}))

;Dispatch macro (#) = Tells clojure reader how to interpret the next character using a read table.
; #{ …​ } - Set
; #_ - Discard
; #"…​" - Regular Expression
; #(…​) - Anonymous function
; #' - Var quote
; ## - Symbolic values
; #inst, #uuid, and #js etc. - tagged literals = tells the reader how to parse the literal value.

;Syntax-quote: ' ~ ~@
; ' =  indicate that the next form should be read but not evaluated.
; ` is the syntax quote
; ~ = Unquote
; :: is used to auto-resolve a keyword in the current namespace.


;eval
(def *foo* "(println [1 2 3])")
*foo*
"(println [1 2 3])"
(eval *foo*)
"(println [1 2 3])"
(eval (read-string *foo*))


;Macro
