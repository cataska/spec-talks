(ns spec-talks.core
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as gen]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))
