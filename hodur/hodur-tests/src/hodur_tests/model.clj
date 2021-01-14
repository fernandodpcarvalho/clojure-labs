(ns hodur-tests.model)

(defn new-person [first-name last-name]
  {:person/first-name first-name
   :person/last-name  last-name})