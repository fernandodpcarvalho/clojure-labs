(ns todo.interceptors
  (:require [io.pedestal.http.route :as route]
            [route-swagger.doc :as sw.doc]
            [schema.core :as s]))

(defn response [status body & {:as headers}]
  {:status status :body body :headers headers})

(def ok (partial response 200))
(def created (partial response 201))
(def accepted (partial response 202))

(def echo
  {:name :echo
   :enter
         (fn [context]
           (let [request  (:request context)
                 response (ok context)]
             (assoc context :response response)))})

(defonce database (atom {}))

(def db-interceptor
  {:name :database-interceptor
   :enter
         (fn [context]
           (update context :request assoc :database @database))
   :leave
         (fn [context]
           (if-let [[op & args] (:tx-data context)]
             (do
               (apply swap! database op args)
               (assoc-in context [:request :database] @database))
             context))})

(defn make-list [list-name]
  {:name  list-name
   :items {}})

(defn make-list-item [item-name]
  {:name  item-name
   :done? false})

(def create-list
  (sw.doc/annotate
    {:summary    "create-list"
     :parameters {:query-params {(s/required-key :name) s/Str}}
     :responses  {201 {:body {(s/required-key :message) s/Str}}}}
    (io.pedestal.interceptor/interceptor
      {:name :list-create
       :enter
             (fn [context]
               (let [list-name (get-in context [:request :query-params :name] "Unnamed List")
                     new-list  (make-list list-name)
                     db-id     (str (gensym "l"))
                     url       (route/url-for :get-list :params {:list-id db-id})]
                 (assoc context
                   :response (created new-list "Location" url)
                   :tx-data [assoc db-id new-list])))})))

(def get-all-lists
  (sw.doc/annotate
    {:summary    "get-all-lists"
     :parameters {}
     :responses  {200 {:body {(s/required-key :message) s/Str}}}}
    (io.pedestal.interceptor/interceptor
      {:name  :get-lists
       :enter (fn [context]
                (if-let [all-lists (get-in context [:request :database])]
                  (if (empty? all-lists)
                    (assoc context :response (ok "Empty todo-list!\n"))
                    (assoc context :response (ok all-lists)))
                  context))})))

(def get-list
  (sw.doc/annotate
    {:summary    "get-lists"
     :parameters {:path-params {(s/required-key :list-id) s/Str}}
     :responses  {200 {:body {(s/required-key :message) s/Str}}}}
    (io.pedestal.interceptor/interceptor
      {:name :get-list
       :enter
             (fn [context]
               (let [db-id    (get-in context [:request :path-params :list-id])
                     database (get-in context [:request :database])]
                 (if-let [the-list (get database db-id)]
                   (assoc context :response (ok the-list))
                   context)))})))

(def entity-render
  {:name :entity-render
   :leave
         (fn [context]
           (if-let [item (:result context)]
             (assoc context :response (ok item))
             context))})

(defn find-list-item-by-ids [dbval list-id item-id]
  (get-in dbval [list-id :items item-id] nil))

(def list-item-view
  {:name :list-item-view
   :leave
         (fn [context]
           (if-let [list-id (get-in context [:request :path-params :list-id])]
             (if-let [item-id (get-in context [:request :path-params :item-id])]
               (if-let [item (find-list-item-by-ids (get-in context [:request :database]) list-id item-id)]
                 (assoc context :result item)
                 context)
               context)
             context))})

(defn list-item-add
  [dbval list-id item-id new-item]
  (if (contains? dbval list-id)
    (assoc-in dbval [list-id :items item-id] new-item)
    dbval))

(def create-list-item
  (sw.doc/annotate
    {:summary    "create-list-item"
     :parameters {:path-params {(s/required-key :list-id) s/Str}
                  :query-params {(s/required-key :name) s/Str}}
     :responses  {201 {:body {(s/required-key :message) s/Str}}}}
    (io.pedestal.interceptor/interceptor
      {:name :list-item-create
       :enter
             (fn [context]
               (if-let [list-id (get-in context [:request :path-params :list-id])]
                 (let [item-name (get-in context [:request :query-params :name] "Unnamed Item")
                       new-item  (make-list-item item-name)
                       item-id   (str (gensym "i"))]
                   (-> context
                     (assoc :tx-data [list-item-add list-id item-id new-item])
                     (assoc-in [:request :path-params :item-id] item-id)))
                 context))})))
