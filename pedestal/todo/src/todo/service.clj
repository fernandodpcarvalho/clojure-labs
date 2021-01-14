(ns todo.service
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.route :as route]
            [pedestal-api.routes :as api.routes]
            [pedestal-api.core :as api]
            [route-swagger.doc :as sw.doc]
            [schema.core :as s]
            [todo.interceptors :refer :all]))

;(def no-csp
;  {:name  ::no-csp
;   :leave (fn [ctx]
;            (assoc-in ctx [:response :headers "Content-Security-Policy"] ""))})

;(defn build-routes [doc routes]
;  (-> routes
;    route/expand-routes
;    api.routes/replace-splat-parameters
;    (api.routes/update-handler-swagger
;      (api.routes/comp->> api.routes/default-operation-ids
;        api.routes/default-empty-parameters))
;    (sw.doc/with-swagger (merge {:basePath ""} doc))))
;
;(def swagger-doc
;  {:info {:title       "Todo list API"
;          :description "Register and consult you todo list"
;          :version     "1.0"}
;   :tags [{:name        "Todo list"
;           :description "Register and consult you todo list"}]})

(def routes
  (route/expand-routes
    #{["/todo" :post [db-interceptor list-create]]
      ["/todo" :get [db-interceptor all-lists-view]]
      ["/todo/:list-id" :get [entity-render db-interceptor list-view]]
      ["/todo/:list-id" :post [entity-render list-item-view db-interceptor list-item-create]]
      ["/todo/:list-id/:item-id" :get [entity-render list-item-view db-interceptor]]
      ["/todo/:list-id/:item-id" :put echo :route-name :list-item-update]
      ["/todo/:list-id/:item-id" :delete echo :route-name :list-item-delete]
      ;["/swagger.json" :get [(api/negotiate-response) (api/body-params) api/common-body (api/coerce-request) (api/validate-response) api/swagger-json]]
      ;["/*resource" :get [(api/negotiate-response) (api/body-params) api/common-body (api/coerce-request) (api/validate-response) no-csp api/swagger-ui]]
      }))

;(defn init-routes []
;  (s/with-fn-validation
;    (build-routes swagger-doc routes)))

(def service
  {:env                     :prod
   ;::http/routes            (init-routes)
   ::http/routes            routes
   ::http/resource-path     "/public"
   ::http/type              :jetty
   ::http/port              8080
   ::http/container-options {:h2c? true
                             :h2?  false
                             :ssl? false}})

;;;;;
;curl --request get localhost:8080/todo
;curl --request post localhost:8080/todo?name=List-A
;curl --request get localhost:8080/todo/l24006
;curl --request post localhost:8080/todo/l24006?name=Item-A
;curl --request get localhost:8080/todo/l24006?name=i24007