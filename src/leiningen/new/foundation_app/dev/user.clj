(ns user
  "Tools for interactive development with the REPL. This file should
  not be included in a production build of the application."
  (:gen-class)
  (:require
   [clojure.edn :as edn]
   [compojure.core :refer (GET POST defroutes)]
   [compojure.route :as route]
   [compojure.handler]
   [cemerick.austin]
   [cemerick.austin.repls :refer (browser-repl-env browser-connected-repl-js)]
   [net.cgrand.enlive-html :as enlive :refer [deftemplate]]
   [ring.adapter.jetty :refer (run-jetty)]
   [ring.middleware.reload :refer (wrap-reload)]
   [ring.util.response :refer (resource-response response content-type)]
   [cljs.closure :as cljsc]
   [clojure.java.shell :refer (sh with-sh-dir)]
   [clojure.java.browse :refer (browse-url)]
   [clojure.java.io :as io]
   [clojure.java.javadoc :refer (javadoc)]
   [clojure.pprint :refer (pprint)]
   [clojure.reflect :refer (reflect)]
   [clojure.repl :refer (apropos dir doc find-doc pst source)]
   [clojure.set :as set]
   [clojure.string :as str]
   [clojure.test :as test]
   [clojure.tools.namespace.repl :refer (refresh refresh-all)]))

(def config (-> (slurp "config/config.edn") edn/read-string))

(defn extract-compiler-options
  [config aspect]
  (let [output-dir (get-in config [:application :output-dir])]
    (reduce (fn [options [k v]]
              (assoc options k (str output-dir "/" v)))
            (select-keys aspect [:optimizations])
            (select-keys aspect [:output-dir :output-to :source-map]))))

(defn get-recordings
  []
  (content-type
   (->> (map #(.getPath %) (file-seq (io/as-file "resources/recordings")))
        rest
        (map #(clojure.string/split % #"/"))
        (map last)
        (map #(clojure.string/replace % #".edn" ""))
        (into [])
        pr-str
        (response))
   "application/edn"))

(deftemplate index
  (io/resource "public/index.html")
  [aspect]
  [:body]
  (let [main (clojure.string/replace (str (:main aspect)) #"-" "_")]
    (enlive/append
     (when (= (:optimizations aspect) :none)
       (enlive/html
        [:script {:src (str (:output-dir aspect) "/goog/base.js")}]))
     (enlive/html [:script {:src (:output-to aspect)}])
     (enlive/html [:script (str "goog.require(\"" main "\");")])
     (enlive/html [:script (str main ".main();")])
     (enlive/html [:script (browser-connected-repl-js)]))))

(defn prepare-route
  [aspect]
  (let [comp-options (extract-compiler-options config aspect)]
    (cljsc/build "src" comp-options)
    (index aspect)))

(defroutes app
  (route/resources "/")
  (GET "*.cljs" [:as req]
       (slurp (str/replace (:uri req) #"^/cljs/resources/public/cljs/" "")))
  (GET "/" [] (prepare-route {:output-dir "cljs"
                              :output-to "cljs/main.js"
                              :source-map "cljs/main.js.map"
                              :optimizations :none
                              :main (quote {{namespace}}.start)}))
  (POST "/recordings/:recording-name"
        [recording-name :as req]
        (spit (str "resources/recordings/" recording-name ".edn")
              (slurp (io/reader (:body req))))
        (response "all good!"))
  (GET "/recordings/:recording-name"
       [recording-name :as req]
       (-> (slurp (str "resources/recordings/" recording-name ".edn"))
           (response)
           (content-type "application/edn")))
  (GET "/recordings"
       [:as req]
       (get-recordings))
  (GET "/data-ui" [] (prepare-route (get-in config [:aspects :data-ui])))
  (GET "/ui" [] (prepare-route (get-in config [:aspects :ui])))
  (GET "/dev" [] (prepare-route (get-in config [:aspects :development])))
  (GET "/prod" [] (prepare-route (get-in config [:aspects :production]))))

(def handler
  (-> app (wrap-reload {:dirs ["dev"]})))

(def system
  "A Var containing an object representing the application under
  development."
  nil)

(defn init
  "Creates and initializes the system under development in the Var
  #'system."
  []
  (alter-var-root
   #'system
   (fn [system]
     (if-not (:server system)
       {:server (doto (run-jetty #'handler {:port 3000 :join? false}) (.start))
        :repl-env (reset! browser-repl-env (cemerick.austin/repl-env))
        :aspects #{}}
       (do (.start (:server system)) system)))))

(defn start
  "Starts the system running, updates the Var #'system."
  []
  (cljsc/build "src" {:output-dir "resources/public/cljs"
                      :output-to "resources/public/cljs/main.js"
                      :source-map "resources/public/cljs/main.js.map"
                      :optimizations :none
                      :main (quote {{namespace}}.start)})
  (browse-url "http://localhost:3000/")
  (cemerick.austin.repls/cljs-repl (:repl-env system)))

(defn stop
  "Stops the system if it is currently running, updates the Var
  #'system."
  []
  (when (try (.stop (:server system))
             (catch Throwable e false))
    true))

(defn go
  "Initializes and starts the system running."
  []
  (init)
  (start)
  :ready)

(defn reset
  "Stops the system, reloads modified source files, and restarts it."
  []
  (stop)
  (refresh :after 'user/go))

(defn -main
  [& args]
  (go))
