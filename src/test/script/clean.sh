#! /bin/sh

cd "$(dirname "$0")"/../../.. || exit 1

find src/test/. -name "*.ass" -exec rm {} \;
find src/main/resources/. -name "*.class" -exec rm {} \;

mvn clean
