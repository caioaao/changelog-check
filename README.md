# changelog-check

A Leiningen plugin to check if current version is documented in changelog

## Install

Put `[changelog-check "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your
project.clj.

## Usage

    $ lein changelog-check path/to/changelog.md

Will check if current project version is documented in changelog. Check the
variable `changelog-version-regex` for info on how it finds versions in the
project. If the path to changelog is omitted, it will try finding the file in
`CHANGELOG.md`.
