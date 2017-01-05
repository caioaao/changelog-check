(defproject changelog-check "0.1.0"
  :description "Plugin to check your changelog"
  :url "https://github.com/caioaao/changelog-check"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/mit-license.php"
            :distribution :repo}
  :eval-in-leiningen true
  :profiles {:dev {:dependencies [[midje "1.8.3"]]}}
  :min-lein-version "2.0.0"
  :deploy-repositories [["releases" :clojars]])
