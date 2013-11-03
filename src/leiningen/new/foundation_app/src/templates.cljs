(ns {{namespace}}.templates
  (:require [dommy.core :as dom])
  (:require-macros [dommy.macros]
                   [foundation.app.templates :refer [deftemplate]]))

(deftemplate {{name}}
  [^:bindings {:keys [id message]}]
  [:div {:id id}
   [:span message]])

(def {{name}}-templates
  {:{{name}} {{name}}})
