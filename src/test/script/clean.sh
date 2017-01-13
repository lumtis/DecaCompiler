#! /bin/sh

cd "$(dirname "$0")"/../../.. || exit 1

find src/test/. -name "*.ass" -exec rm {} \;

mvn clean
