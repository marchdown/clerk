;; # Pagination
(ns notebooks.pagination
  (:require [babashka.fs :as fs]
            [nextjournal.clerk :as clerk]))

#_(nextjournal.clerk/show! "notebooks/pagination.clj")

(range)

{:a [(range)]}

(def notebooks
  (clojure.java.io/file "notebooks"))

(def words-path "/usr/share/dict/words")

(when-let [words (and (fs/exists? words-path) (slurp words-path))]
  (subs words 0 10100))

[notebooks]

(into #{} (map str) (file-seq notebooks))

(def r (range 100))

(map inc r)

[(mapv inc r)]

^:nextjournal.clerk/no-cache (shuffle r)

;; A long list.
(range 1000)

;; A somewhat large map.
(zipmap (range 1000) (map #(* % %) (range 1000)))

^:nextjournal.clerk/no-cache (shuffle (range 42))

(let [[first-three others] (split-at 3 [:A :A :B :B :C :C :D :D])]
  {:first-three first-three
   :others others})

(frequencies [:A :A :B :B :C :C :D :D])

(group-by first [[:A :B :B] [:B :C :C] [:C :A :A]])

(take 100
      (repeatedly (fn []
                    {:name (str
                            (rand-nth ["Oscar" "Karen" "Vlad" "Rebecca" "Conrad"]) " "
                            (rand-nth ["Miller" "Stasčnyk" "Ronin" "Meyer" "Black"]))
                     :role (rand-nth [:admin :operator :manager :programmer :designer])
                     :id (java.util.UUID/randomUUID)
                     :created-at #inst "2021"})))

(defn flat->nested
  [root coll]
  (if-let [children (seq (filter #(= (:id root) (:parent %)) coll))]
    (map #(assoc root :children (flat->nested % coll)) children)
    (list root)))

(let [items (concat [{:id 0 :parent nil :name "item-0"}]
                    (for [x (range 1 5)]
                      {:id x :parent (dec x) :name (format "item-%d" x)}))]
  (flat->nested (-> (filter #(= (:parent %) nil) items) first) items))


^{::clerk/budget 5}
(reduce (fn [acc i] (vector i acc)) :fin (range 15 0 -1))

(clerk/with-viewer {}
  {::clerk/budget 5}
  (reduce (fn [acc i] (vector i acc)) :fin (range 15 0 -1)))

(clerk/html [:div
             [:h3 "Nesting Images inside " [:span.font-mono "clerk/html"]]
             (clerk/image "trees.png")])

(clerk/html [:div
             [:h3 "Nesting paginated collections inside " [:span.font-mono "clerk/html"]]
             [:ul
              [:li "A strong range: "
               [:p [:strong {:nextjournal/value (range 30)}]]]
              [:li "A slanted range:"
               [:p [:em {:nextjournal/value (range 100)}]]]]])

(clerk/html
 {::clerk/budget 5}
 [:div
  [:h3 "Configuring budget inside " [:span.font-mono "clerk/html"]]
  {:nextjournal/value (reduce (fn [acc i] (vector i acc)) :fin (range 15 0 -1))}])
