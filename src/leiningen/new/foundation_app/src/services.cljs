(ns {{namespace}}.services
    (:require [cljs.core.async :refer [put!]]))

(defn echo-services-fn
  [message queue]
  (put! queue message))
