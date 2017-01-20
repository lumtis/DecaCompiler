#! /bin/sh

cd "$(dirname "$0")"/../../.. || exit 1

find src/. -name "*.ass" -exec rm {} \;
find src/main/resources/. -name "*.class" -exec rm {} \;

mvn clean
