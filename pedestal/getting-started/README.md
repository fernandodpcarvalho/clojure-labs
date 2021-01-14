# hello-world

http://pedestal.io/guides/hello-world

# REPL

(require '[hello-world.hello :as hello])

(hello/start)

curl -i http://localhost:8890/greet



# hello-world-query-parameters

http://pedestal.io/guides/hello-world-query-parameters

# REPL

(require '[hello-world-query-parameters.hello :as hello])

(hello/start)

curl -i http://localhost:8890/greet\?name=Fernando



# hello-world-content-types

http://pedestal.io/guides/hello-world-content-types

# REPL

(require '[hello-world-content-types.hello :as hello])

(hello/start)

curl -i http://localhost:8890/greet\?name=Fernando

curl http://localhost:8890/echo

curl -i http://localhost:8890/greet

curl -i -H "Accept: text/html" http://localhost:8890/greet

curl -i -H "Accept: application/edn" http://localhost:8890/greet

curl -i -H "Accept: application/xml, application/json" http://localhost:8890/greet


# todo-api

http://pedestal.io/guides/your-first-api

The service will be a simple "to-do" list that we keep in memory. 


Route	                  Verb	   Action	          Response

/todo                     POST     Create a new list     201 with URL of new list

/todo                     GET      Return query form     200 with static page 

/todo/:list-id            GET      View an list          200 with all items 

/todo/:list-id/:item-id   GET      View an item          200 with item 

/todo/:list-id/:item-id   PUT      Update an item        200 with updated item 


# Comands

(require 'main)

(main/start-dev)

@main/database

(main/test-request :post "/todo?name=A")

(main/test-request :get "/todo/:list-id")

(main/test-request :post "/todo/:list-id?name=ItemA")

(main/test-request :get "/todo/:list-id/:item-id")

(main/test-request :get "/todo")