#!/usr/bin/env bash

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

for cas_de_test in src/test/deca/context/valid/*.deca
do
    res=$(test_decompile "$cas_de_test");
    echo "$res";
    echo "";
    echo "Appuyez sur une touche pour continuer.";
    read nimp;
done