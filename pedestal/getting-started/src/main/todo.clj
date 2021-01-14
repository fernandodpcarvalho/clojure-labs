(ns main.todo
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [io.pedestal.test :as test]))

(defn response [status body & {:as headers}]
  {:status status :body body :headers headers})

(def ok       (partial response 200))
(def created  (partial response 201))
(def accepted (partial response 202))

(def echo
  {:name :echo
   :enter
         (fn [context]
           (let [request (:request context)
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

(def list-create
  {:name :list-create
   :enter
         (fn [context]
           (let [list-name (get-in context [:request :query-params :name] "Unnamed List")
                 new-list  (make-list list-name)
                 db-id     (str (gensym "l"))
                 url       (route/url-for :list-view :params {:list-id db-id})]
             (println "url")
             (println url)
             (assoc context
               :response (created new-list "Location" url)
               :tx-data [assoc db-id new-list])))})

(def all-lists-view
  {:name  :get-lists
   :enter (fn [context]
            (if-let [all-lists (get-in context [:request :database])]
              (if (empty? all-lists)
                (assoc context :response (ok "Empty todo-list!\n"))
                (assoc context :response (ok all-lists)))
              context))})

(defn find-list-by-id [dbval db-id]
  (get dbval db-id))

(def list-view
  {:name :list-view
   :enter
         (fn [context]
           (if-let [db-id (get-in context [:request :path-params :list-id])]
             (if-let [the-list (find-list-by-id (get-in context [:request :database]) db-id)]
               (assoc context :result the-list)
               context)
             context))})

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

(def list-item-create
  {:name :list-item-create
   :enter
         (fn [context]
           (if-let [list-id (get-in context [:request :path-params :list-id])]
             (let [item-name (get-in context [:request :query-params :name] "Unnamed Item")
                   new-item  (make-list-item item-name)
                   item-id   (str (gensym "i"))]
               (-> context
                   (assoc :tx-data  [list-item-add list-id item-id new-item])
                   (assoc-in [:request :path-params :item-id] item-id)))
             context))})

(def routes
  (route/expand-routes
    #{["/todo"                    :post   [db-interceptor list-create]]
      ["/todo"                    :get    [db-interceptor all-lists-view]]
      ["/todo/:list-id"           :get    [entity-render db-interceptor list-view]]
      ["/todo/:list-id"           :post   [entity-render list-item-view db-interceptor list-item-create]]
      ["/todo/:list-id/:item-id"  :get    [entity-render list-item-view db-interceptor]]
      ["/todo/:list-id/:item-id"  :put    echo :route-name :list-item-update]
      ["/todo/:list-id/:item-id"  :delete echo :route-name :list-item-delete]}))

;In table syntax, each route is a vector of:
;Path string
;Verb. One of :any, :get, :put, :post, :delete, :patch, :options, :head
;Handler or vector of interceptors
;Optional route name clause (Ex: :route-name :new-user)
;Optional constraint clause (Ex: :constraints {:id #"[0-9]+"})

;Pedestal requires that every route must have a unique name.
;Using the echo interceptor everywhere, there was no way for Pedestal to create unique, meaningful names for the routes. So we had to help it out.
;Now, list-create has it’s own name so we can let Pedestal do it’s default behavior: use the name of the last interceptor in the vector.

(def service-map
  {::http/routes routes
   ::http/type   :jetty
   ::http/port   8890})

(defn start []
  (http/start (http/create-server service-map)))

;; For interactive development
(defonce server (atom nil))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                        (assoc service-map
                          ::http/join? false)))))

(defn stop-dev []
  (http/stop @server))

(defn restart-dev []
  (stop-dev)
  (start-dev))

(defn test-request [verb url]
  (io.pedestal.test/response-for (::http/service-fn @server) verb url))

#_(start-dev)
#_(stop-dev)
#_(restart-dev)

#_(test-request :post "/todo?name=List-A")
#_(test-request :post "/todo?name=List-B")
#_(test-request :post "/todo?name=List-C")

#_(test-request :get "/todo")

#_(test-request :get "/todo/l17574")

#_(test-request :post "/todo/l17574?name=ItemA")
#_(test-request :post "/todo/l17574?name=ItemB")
#_(test-request :post "/todo/l17574?name=ItemC")

#_(test-request :get "/todo/l17574/i17688")
