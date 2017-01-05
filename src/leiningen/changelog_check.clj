(ns leiningen.changelog-check
  (:require [clojure.string :as str]
            [leiningen.core.main :refer [abort info warn]]))

(def changelog-version-regex #"(?m)^## \[(.*)\].*$")

(defn version-raw->versions
  "Returns seq with (major minor bugfix) versions"
  [s]
  (letfn [(to-int [x] (Integer/parseInt x))]
    (map to-int (-> (str/split s #"-")
                    first
                    (str/split #"\.")))))

(defn changelog-raw->versions
  [s]
  (->> (re-seq changelog-version-regex s)
       (map (comp version-raw->versions second))))


(defn described-versions
  [file]
  (-> (slurp file)
        changelog-raw->versions))

(defn bugfix?
  [[_ _ minor-version]]
  (not (zero? minor-version)))

(defn version-documented?
  [ver changelog-path]
  (or (bugfix? ver) ;; Bugfixes are not checked
      (some #{ver} (described-versions changelog-path))))

(defn project-version
  [project]
  (-> project
      :version
      version-raw->versions))


(defn ^:no-project-needed changelog-check
  "Checks if current is documented"
  ([project changelog-path]
   (if (-> (project-version project)
           (version-documented? changelog-path))
     (info "Version" (:version project) "documented. Congrats!")
     (abort "Version" (:version project) "not documented in" changelog-path)))
  ([project]
   (changelog-check project "./CHANGELOG.md")))
