(ns {{namespace}}.behavior-test
    (:require [foundation.app :as app]
              [foundation.app.tree :as tree]
              [foundation.app.messages :as msg]
              [foundation.app.render :as render]
              [foundation.app.test :as test]
              [{{namespace}}.behavior :refer [{{raw-name}}]]
              [foundation.app.query :only [q]])
    (:require-macros [cemerick.cljs.test :refer [is deftest run-tests testing]]))

;; Test a transform function

(deftest test-set-value-transform
  (is (= (set-value-transform
          {} {msg/type :set-value msg/topic [:greeting] :value "x"})
         "x")))

;; Build an application, send a message to a transform and check the transform
;; state

(deftest test-app-state
  (let [app (app/build {{raw-name}})]
    (app/begin app)
    (is (vector?
         (test/run-sync! app
                         [{msg/type :set-value msg/topic [:greeting] :value "x"}])))
    (is (= (-> app :state deref :data-model :greeting) "x"))))

;; Use foundation.app.query to query the current application model

(deftest test-query-ui
  (let [app (app/build {{raw-name}})
        app-model (render/consume-app-model app (constantly nil))]
    (app/begin app)
    (is (test/run-sync!
         app [{msg/topic [:greeting] msg/type :set-value :value "x"}]))
    (is (= (q '[:find ?v
                :where
                [?n :t/path [:greeting]]
                [?n :t/value ?v]]
              @app-model)
           [["x"]]))))
