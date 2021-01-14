(ns testes.promesa
  (:require [promesa.core :as p]
            [promesa.exec :as exec]))

;; creates a promise from value
(p/promise 1)

;; creates a rejected promise
(p/promise (ex-info "error" {}))

;; Create a resolved promise
(p/resolved 1)
;; => #object[java.util.concurrent.CompletableFuture 0x3e133219 "resolved"]

;; Create a rejected promise
(p/rejected (ex-info "error" {}))
;; => #object[java.util.concurrent.CompletableFuture 0x3e563293 "rejected"]

(defn create-promise []
  (let [p (p/deferred)]
    (future (p/resolve! p "resolved-value"))
    p))
(p/let [pp (create-promise)] pp)
@(p/let [pp (create-promise)] pp)


(defn sleep [ms]
  (let [p (p/deferred)]
    (future (p/resolve! p ((fn [v] (Thread/sleep v) (println v)) ms)))
    p))
(p/let [pp (sleep 5000)] pp)
@(p/let [pp (sleep 5000)] pp)

(def ppp (p/promise ((fn [v] (Thread/sleep v) (println v)) 3000)))
(println ppp)
(def ppf (p/promise (future ((fn [v] (Thread/sleep v) (println v)) 3000))))
(println ppf)
@ppp





;factory function
@(p/create (fn [resolve reject] (resolve 1)))
;; => 1

;provide an executor
@(p/create (fn [resolve reject] (resolve 1)) exec/default-executor)
;; => 1

;Using do! macro
(p/do!
  (let [a (rand-int 10)
        b (rand-int 10)]
    (+ a b)))


;The most common way to chain a transformation to a promise is using the general purpose then function:
@(-> (p/resolved 1)
     (p/then inc))
;; => 2


(def p (p/promise 2))
(println p)
(println @p)


(def p-vazia (p/deferred))
(println p)
(println (p/resolve! p-vazia "oxente"))


;future
(def f (future
         (println "Future start")
         (Thread/sleep 10000)
         (println "Future done") 100))
@f




;;;;;;;;;;;;;;;;;;
(defn sleep-promise
  [wait]
  (p/promise (fn [resolve reject]
               (exec/schedule! wait #(resolve wait)))))

(def result
  (p/let [x (sleep-promise 42)
          y (sleep-promise 41)
          z 2]
         (+ x y z)))

@result
;; => 85


;;;;;













(def psoma (p/promise (+ 1 3)))
(p/resolve! psoma)
(println psoma)



(defn read-file [filename]
  (let [p (p/deferred)]
    (readFile filename (fn [err data]
                         (if err
                           (p/reject! p err)
                           (p/resolve! p data))))
    p))


(defn lint-file [path]
  (p/let [file (-> path
                   read-file)]
         (-> file
             str
             spectral-lint)))



(let [p (p/deferred)]
  (future (p/resolve! p))
  p)


;;;;;;;;;;;;;;;;;;;;

