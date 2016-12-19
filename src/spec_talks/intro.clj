(ns spec-talks.intro
  (:require [clojure.spec :as s]
            [clojure.spec.gen :as gen]))

;; Conform
(s/conform even? 1000) ;;=> 1000

;; Valid
(s/valid? nil? nil) ;; true
(s/valid? string? "abc") ;; true

(s/valid? #(> % 5) 10) ;; true
(s/valid? #(> % 5) 0) ;; false

;; Set
(s/valid? #{:club :diamond :heart :spade} :club) ;; true
(s/valid? #{:club :diamond :heart :spade} 42) ;; false

(s/valid? #{42} 42) ;; true

;; s/def
(s/def ::date inst?)
(s/def ::suit #{:club :diamond :heart :spade})

;; Keywords
:harry ;; => :harry
(keyword "harry") ;; => :harry

;; Keywords with namespace
:hogwarts/harry ;; => :hogwarts/harry
(keyword "hogwarts" "harry") ;; => :hogwarts/harry

;; Double colon
;; in ns hogwarts
(ns hogwarts)
::harry ;; => :hogwarts/harry
(= ::harry :hogwarts/harry) ;; => true

;; Switch namespace back
(ns spec-talks.intro)

;; Explain
(s/explain ::suit 42)
;; val: 42 fails spec: ::suit predicate: #{:spade :heart :diamond :club}

;; s/and
(s/def ::big-even (s/and int? even? #(> % 1000)))

(s/valid? ::big-even :foo) ;; false
(s/valid? ::big-even 10) ;; false
(s/valid? ::big-even 100000) ;; true

;; s/or
(s/def ::name-or-id (s/or :name string?
                          :id   int?))

(s/valid? ::name-or-id "abc") ;; true
(s/valid? ::name-or-id 100) ;; true
(s/valid? ::name-or-id :foo) ;; false

(s/conform ::name-or-id "abc") ;;=> [:name "abc"]
(s/conform ::name-or-id 100) ;;=> [:id 100]

;; coll-of
(s/conform (s/coll-of keyword?) [:a :b :c]) ;;=> [:a :b :c]
(s/conform (s/coll-of number?) #{5 10 2}) ;;=> #{2 5 10}

;; tuple
(s/conform (s/tuple double? double? double?) [1.0 2.0 3.0])
;;=> [1.0, 2.0, 3.0]
(s/conform (s/tuple double? double? double?) [1.0 2.0 3.0 4.0])
;;=> s/invalid

;; map-of
(s/def ::scores (s/map-of string? int?))
(s/conform ::scores {"Sally" 1000, "Joe" 500})
;=> {"Sally" 1000, "Joe" 500}

;; Exercise
(s/exercise ::suit)
;; ([:heart :heart] [:heart :heart] [:spade :spade]
;;  [:diamond :diamond] [:diamond :diamond] [:spade :spade]
;;  [:club :club] [:diamond :diamond] [:spade :spade] [:club :club])

;; Generators
(s/gen int?)
(s/gen string?)

;; Generate
(gen/generate (s/gen int?)) ;; -124
(gen/generate (s/gen string?)) ;; "ztg6Q064P4tBS758dt2k7F5Qa0Z"

;; Sample
(gen/sample (s/gen int?))
;; (-1 0 -1 1 -5 7 0 -36 -4 13)
(gen/sample (s/gen string?))
;; ("" "7" "7" "0SF" "1sJO" "EE7L" "7" "50" "2G4wNNO" "7G223cq")
