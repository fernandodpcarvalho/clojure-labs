(defproject hodur-tests "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.datomic/datomic-pro "0.9.6045"]
                 [hodur/engine "0.1.7"]
                 [hodur/spec-schema "0.1.5"]
                 [hodur/datomic-schema "0.1.0"]
                 [prismatic/schema "1.1.12"]]
  :repl-options {:init-ns hodur-tests.core})
