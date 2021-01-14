(ns hodur-tests.db
  (:use clojure.pprint)
  (:require [datomic.api :as d]))

;Conexão Datomic
(def db-uri "datomic:dev://localhost:4334/hodur-test")
(defn apaga-banco []
  (d/delete-database db-uri))
​
(defn abre-conexao []
  (d/create-database db-uri)
  (d/connect db-uri))