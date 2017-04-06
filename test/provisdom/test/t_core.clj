(ns provisdom.test.t-core
  (:require
    [clojure.test :refer :all]
    [clojure.spec :as s]
    [clojure.spec.test :as st]
    [provisdom.test.core :as t]))

(deftest test-macro-expansions
  (are [expected-form quoted-form] (= expected-form (macroexpand-1 quoted-form))
    `(is (~'= 1 1) nil) `(t/is= 1 1 nil)
    `(is (~'not false) "some message") `(t/is-not false "some message")))

(deftest t-midje-just
  (are [e a] (t/midje-just e a)
    [1 1 1] [1 1 1]
    [1 1 #(and (number? %) (not (== % %)))] [1 1 Double/NaN])
  (are [e a] (not (t/midje-just e a))
    [1 1 1] [1 1 1.0]))