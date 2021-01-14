(ns todo.service
  (:require [io.pedestal.http :as http]
            [pedestal-api.core :as api]
            [schema.core :as s]
            [todo.interceptors :refer :all]))

(def no-csp
  {:name  ::no-csp
   :leave (fn [ctx]
            (assoc-in ctx [:response :headers "Content-Security-Policy"] ""))})

(def swagger-doc
  {:info {:title       "Todo list API"
          :description "Register and consult you todo list"
          :version     "1.0"}
   :tags [{:name        "Todo list"
           :description "Register and consult you todo list"}]})

(def routes
  #{["/todo" :post [db-interceptor (api/doc {:tags ["create-list"]}) create-list]]
    ["/todo" :get [db-interceptor (api/doc {:tags ["get-all-lists"]}) get-all-lists]]
    ["/todo/:list-id" :get [entity-render db-interceptor (api/doc {:tags ["get-list"]}) get-list]]
    ["/todo/:list-id" :post [entity-render list-item-view db-interceptor (api/doc {:tags ["create-list-item"]}) create-list-item]]
    ["/todo/:list-id/:item-id" :get [entity-render list-item-view db-interceptor]]
    ["/todo/:list-id/:item-id" :put echo :route-name :list-item-update]
    ["/todo/:list-id/:item-id" :delete echo :route-name :list-item-delete]
    ["/swagger.json" :get [(api/negotiate-response) (api/body-params) api/common-body (api/coerce-request) (api/validate-response) api/swagger-json]]
    ;["/*resource" :get [no-csp api/swagger-ui]]
    })

(s/with-fn-validation
  (api/defroutes routes swagger-doc routes))

(def service
  {:env                     :prod
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