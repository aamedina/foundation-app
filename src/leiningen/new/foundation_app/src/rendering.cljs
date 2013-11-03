(ns {{namespace}}.rendering
    (:require [domina :as d]
              [dommy.core :as dom]
              [{{namespace}}.templates :as t]
              [foundation.app.render.push :as render]
              [foundation.app.render.templates :as templates]
              [foundation.app.render.automatic :as auto])
    (:require-macros [dommy.macros]))

(def templates t/{{name}}-templates)

(defn render-page [renderer [_ path] transmitter]
  (let [parent (render/get-parent-id renderer path)
        id (render/new-id! renderer path)
        html (templates/add-template renderer path (:{{name}} templates))]
    (dom/append! (d/by-id parent) (html {:id id}))))

(defn render-message [renderer [_ path _ new-value] transmitter]
  (templates/update-t renderer path {:message new-value}))

(defn render-config []
  [[:node-create [:greeting] render-page]
   [:node-destroy [:greeting] auto/default-exit]
   [:value [:greeting] render-message]])
