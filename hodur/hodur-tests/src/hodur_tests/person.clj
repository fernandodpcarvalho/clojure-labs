(ns hodur-tests.person
    (:use clojure.pprint)
    (:require [hodur-engine.core :as hodur]
              [hodur-datomic-schema.core :as hodur-datomic]
              [datomic.api :as d]
              [hodur-tests.db :as db]
              [hodur-tests.model :as model]
              [schema.core :as s))

(db/apaga-banco)
(def conn (db/abre-conexao))
​
;Initialize an atom representing the meta-database of your model (function hodur/init-schema)
;defining a Person entity
;Expand Person model above by "tagging" the Person entity for Datomic (:datomic/tag-recursive true)
(def meta-db (hodur/init-schema
               '[^{:datomic/tag-recursive true}
                 Person
                 [^String first-name
                  ^String last-name]]))
​
;Function schema that generates your model as a Datomic schema payload:
(def datomic-schema (hodur-datomic/schema meta-db))
(pprint datomic-schema)
​
(defn create-schema []
      (d/transact conn datomic-schema))
(create-schema)

(def person (model/new-person "Fernando", "Carvalho")) ​
(pprint person)
(d/transact conn [person])
​
;Query
;Snapshot bd
(def db (d/db conn))
(d/q '[:find ?e ?first-name ?last-name
       :where [?e person/first-name]
       [?e person/first-name ?first-name]
       [?e person/last-name ?last-name]] db)

;;;;;;;;TESTES
;(defn transaction [vector]
;      (if (= clojure.lang.PersistentVector (class vector))
;        (pprint vector)
;        (throw
;          (ex-info "The second parameter of the transaction function must be a clojure.lang.PersistentVector"
;                   {:param-received vector :error-type :wrong-parameter-type}))))
;
;(transaction "[1]")