(ns leiningen.new.foundation-app
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files
                                             project-name sanitize-ns]]
            [leiningen.core.main :as main]))

(def render (renderer "foundation-app"))

(defn base-files
  [render data]
  [["README.md" (render "README.md" data)]
   
   ["project.clj" (render "project.clj" data)]

   ["src/{{sanitized}}/behavior.cljs" (render "src/behavior.cljs" data)]
   ["src/{{sanitized}}/templates.cljs" (render "src/templates.cljs" data)]
   ["src/{{sanitized}}/services.cljs" (render "src/services.cljs" data)]
   ["src/{{sanitized}}/rendering.cljs" (render "src/rendering.cljs" data)]
   ["src/{{sanitized}}/start.cljs" (render "src/start.cljs" data)]
   ["src/{{sanitized}}/simulated/start.cljs"
    (render "src/simulated/start.cljs" data)]
   ["src/{{sanitized}}/simulated/services.cljs"
    (render "src/simulated/services.cljs" data)]

   ["resources/Gruntfile.js" (render "resources/Gruntfile.js" data)]
   ["resources/package.json" (render "resources/package.json" data)]
   
   "resources/recordings"
   
   ["resources/less/alerts.less"
    (render "resources/less/alerts.less" data)]
   ["resources/less/badges.less"
    (render "resources/less/badges.less" data)]
   ["resources/less/bootstrap.less"
    (render "resources/less/bootstrap.less" data)]
   ["resources/less/bootstrap-overrides.less"
    (render "resources/less/bootstrap-overrides.less")]
   ["resources/less/breadcrumbs.less"
    (render "resources/less/breadcrumbs.less" data)]
   ["resources/less/button-groups.less"
    (render "resources/less/button-groups.less" data)]
   ["resources/less/buttons.less"
    (render "resources/less/buttons.less" data)]
   ["resources/less/carousel.less"
    (render "resources/less/carousel.less" data)]
   ["resources/less/close.less"
    (render "resources/less/close.less" data)]
   ["resources/less/code.less"
    (render "resources/less/code.less" data)]
   ["resources/less/component-animations.less"
    (render "resources/less/component-animations.less" data)]
   ["resources/less/dropdowns.less"
    (render "resources/less/dropdowns.less" data)]
   ["resources/less/forms.less"
    (render "resources/less/forms.less" data)]
   ["resources/less/glyphicons.less"
    (render "resources/less/glyphicons.less" data)]
   ["resources/less/navbar.less"
    (render "resources/less/navbar.less" data)]
   ["resources/less/normalize.less"
    (render "resources/less/normalize.less" data)]
   ["resources/less/navs.less"
    (render "resources/less/navs.less" data)]
   ["resources/less/tables.less"
    (render "resources/less/tables.less" data)]
   ["resources/less/theme.less"
    (render "resources/less/theme.less" data)]
   ["resources/less/thumbnails.less"
    (render "resources/less/thumbnails.less" data)]
   ["resources/less/grid.less"
    (render "resources/less/grid.less" data)]
   ["resources/less/input-groups.less"
    (render "resources/less/input-groups.less" data)]
   ["resources/less/jumbotron.less"
    (render "resources/less/jumbotron.less" data)]
   ["resources/less/labels.less"
    (render "resources/less/labels.less" data)]
   ["resources/less/list-group.less"
    (render "resources/less/list-group.less" data)]
   ["resources/less/media.less"
    (render "resources/less/media.less" data)]
   ["resources/less/mixins.less"
    (render "resources/less/mixins.less" data)]
   ["resources/less/modals.less"
    (render "resources/less/modals.less" data)]
   ["resources/less/pager.less"
    (render "resources/less/pager.less" data)]
   ["resources/less/pagination.less"
    (render "resources/less/pagination.less" data)]
   ["resources/less/panels.less"
    (render "resources/less/panels.less" data)]
   ["resources/less/popovers.less"
    (render "resources/less/popovers.less" data)]
   ["resources/less/print.less"
    (render "resources/less/print.less" data)]
   ["resources/less/progress-bars.less"
    (render "resources/less/progress-bars.less" data)]
   ["resources/less/responsive-utilities.less"
    (render "resources/less/responsive-utilities.less" data)]
   ["resources/less/scaffolding.less"
    (render "resources/less/scaffolding.less" data)]
   ["resources/less/tooltip.less"
    (render "resources/less/tooltip.less" data)]
   ["resources/less/type.less"
    (render "resources/less/type.less" data)]
   ["resources/less/utilities.less"
    (render "resources/less/utilities.less" data)]
   ["resources/less/variables.less"
    (render "resources/less/variables.less" data)]
   ["resources/less/wells.less"
    (render "resources/less/wells.less" data)]
   
   ["resources/public/404.html" (render "resources/public/404.html" data)]
   ["resources/public/favicon.ico" (render "resources/public/favicon.ico" data)]
   ["resources/public/index.html" (render "resources/public/index.html" data)]

   ["resources/public/fonts/glyphicons-halflings-regular.eot"
    (render "resources/public/fonts/glyphicons-halflings-regular.eot" data)]
   ["resources/public/fonts/glyphicons-halflings-regular.svg"
    (render "resources/public/fonts/glyphicons-halflings-regular.svg" data)]
   ["resources/public/fonts/glyphicons-halflings-regular.ttf"
    (render "resources/public/fonts/glyphicons-halflings-regular.ttf" data)]
   ["resources/public/fonts/glyphicons-halflings-regular.woff"
    (render "resources/public/fonts/glyphicons-halflings-regular.woff" data)]
   
   ["resources/public/css/bootstrap.min.css"
    (render "resources/public/css/bootstrap.min.css" data)]
   ["resources/public/css/pedestal.css"
    (render "resources/public/css/pedestal.css" data)]
   ["resources/public/css/goog.css"
    (render "resources/public/css/goog.css" data)]
   ["resources/public/js/bootstrap.min.js"
    (render "resources/public/js/bootstrap.min.js" data)]
   ["resources/public/js/jquery.min.js"
    (render "resources/public/js/vendor/jquery-1.10.2.min.js" data)]
   ["resources/public/js/api.js"
    (render "resources/public/js/api.js" data)]
   
   ["config/config.edn" (render "config/config.edn" data)]

   ["dev/user.clj" (render "dev/user.clj" data)]
   
   ["test/{{sanitized}}/behavior_test.cljs"
    (render "test/behavior_test.cljs" data)]])

(defn foundation-app
  [name]
  (let [main-ns (sanitize-ns name)
        data {:name (project-name name)
              :raw-name name
              :sanitized (name-to-path main-ns)
              :namespace main-ns}]
    (main/info "Generating fresh foundation-app project.")
    (apply ->files data
           (base-files render data))))
