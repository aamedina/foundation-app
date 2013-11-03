(ns {{namespace}}.start
    (:require [clojure.browser.repl]
              [foundation.app :as app]
              [foundation.app.render.push :as push-render]
              [foundation.app.render :as render]
              [foundation.app.messages :as msg]
              [cljs.core.async :refer [put!]]
              [{{namespace}}.behavior :as behavior]
              [{{namespace}}.rendering :as rendering]))

(defn create-app [render-config]
  (let [app (app/build behavior/{{name}})
        render-fn (push-render/renderer "content" render-config render/log-fn)
        app-model (render/consume-app-model app render-fn)]
    (app/begin app)
    (put! (:input app)
          {msg/type :set-value msg/topic [:greeting] :value "Hello World!"})
    {:app app :app-model app-model}))

(defn ^:export main
  []
  (create-app (rendering/render-config)))

