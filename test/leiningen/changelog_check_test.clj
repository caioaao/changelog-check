(ns leiningen.changelog-check-test
  (:require [leiningen.changelog-check :refer :all]
            [midje.sweet :refer :all]))


(def changelog-sample
  "# Changelog

## [9.31.0]
- Lorem ipsum dolor `sit` amet;
- Consectetur adipiscing elit. Phasellus pharetra, nunc in mattis pretium, risus nunc eleifend elit, vitae scelerisque `tortor` ante sit `amet` urna.

## [9.29.0]
-  Donec finibus facilisis dui.

## [9.28.0]
- Sit amet pharetra quam tempus in. Praesent iaculis vehicula enim, sed congue augue semper quis. Ut in efficitur ex.

## [9.26.0]
- Duis auctor viverra eros (ut tristique elit convallis gravida);
- Nam mattis dolor eget nibh pharetra varius ut finibus mauris;
- Praesent turpis risus, tincidunt in nibh id, volutpat posuere augue.

## [9.25.1]
- Etiam ac elit id urna `commodo.auctor.vitae/quis->nisl`;

## [9.25.0]
- Cras euismod maximus `porttitor`.
- Support global schema names with `common-core.schema/named`

## [9.24.0]
-  Vestibulum `ut/lectus->eu` urna venenatis interdum;
-  Donec `pellentesque/molestie->lacinia`.
")

(facts "`version-raw->versions` coerce string to seq of versions"
       (version-raw->versions "12.13.14") => [12 13 14]
       (version-raw->versions "0.1.8-SNAPSHOT") => [0 1 8])

(facts "`changelog-raw->versions` works on our changelog repr"
       (changelog-raw->versions changelog-sample)
       => [[9 31 0] [9 29 0] [9 28 0] [9 26 0] [9 25 1] [9 25 0] [9 24 0]])

(facts "`bugfix?` only returns true if version doesn't end with zeroes"
       (bugfix? [..major.. ..minor.. 0]) => falsey
       (bugfix? [..major.. ..minor.. 10]) => truthy
       (bugfix? [..major.. ..minor.. 123]) => truthy)

(tabular
 (fact "`version-documented?` reads a file and checks if versions are ok"
       (version-documented? ?ver ..file..) => ?ok?
       (provided (slurp ..file..) => changelog-sample :times [0 1]))

 ?ver         ?ok?
 [9 31 0]     truthy
 [9 30 0]     falsey
 [9 25 0]     truthy
 [9 27 0]     falsey
 [9 123 1231] truthy)
