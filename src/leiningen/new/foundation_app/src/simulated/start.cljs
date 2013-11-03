(ns {{namespace}}.simulated.start
    (:require [clojure.browser.repl]
              [foundation.app.render.automatic :as d]
              [{{namespace}}.start :as start]
              [{{namespace}}.rendering :as rendering]
              [goog.Uri]
              [foundation.app-tools.rendering-view.client :as client]
              [foundation.app-tools.rendering-view.record :as record]))

(defn param
  [name]
  (let [uri (goog.Uri. (.toString  (.-location js/document)))]
    (.getParameterValue uri name)))

(defn ^:export main
  []
  (start/create-app (if (= "auto" (param "renderer"))
                      d/data-renderer-config
                      (rendering/render-config))))

