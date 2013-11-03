(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :repositories
  {"sonatype-oss-public" "https://oss.sonatype.org/content/groups/public/"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1978"]
                 [org.clojure/tools.namespace "0.2.4"]
                 [foundation/core.async "0.1.0"]
                 [foundation "0.1.0"]
                 [cljs-time "0.1.0"]
                 [ring "1.2.0"]
                 [compojure "1.1.5"]
                 [prismatic/dommy "0.1.2"]
                 [domina "1.0.2"]
                 [enlive "1.1.4"]]
  :min-lein-version "2.0.0"
  :source-paths ["dev" "src"]
  :main user
  :plugins [[com.cemerick/austin "0.1.1"]
            [com.cemerick/clojurescript.test "0.1.0"]]
  :jvm-opts ["-Xmx1g" "-server"]
  :cljs-path "resources/public/cljs"
  :repl-path ".repl"
  :out-path "out"
  :clean-targets [:target-path :cljs-path :repl-path :out-path])
