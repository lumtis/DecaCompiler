#!/usr/bin/env bash

#Programme pour tester l'ensemble des tests du programmes et lancer le rapport de couverture de Cobertura

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

clean.sh
mvn test-compile

mvn cobertura:clean
mvn cobertura:instrument

basic-lex.sh
basic-synt.sh
basic-context.sh
basic-decac.sh
basic-decompile.sh
basic-gencode.sh
common-tests.sh

cobertura-report.sh
firefox target/site/cobertura/index.html &