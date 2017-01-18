#!/usr/bin/env bash


# On décompile un programme, puis on exécute decac sur l'arbre décompilé et on
# redécompile l'arbre généré. Enfin, on compare les 2 arbres décompilés.
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

test_decompile_valid() {
    res=$(test_decompile "$1");
    touch "fichier_decompile_temporaire~";
    echo "$res" > fichier_decompile_temporaire~;
    res2=$(test_decompile "fichier_decompile_temporaire~");
    rm "fichier_decompile_temporaire~";
    if [ "$res" == "$res2" ]; then
        echo "$1 : PASS."
    else
        echo "$1 : ERROR."
    fi
}
echo "-------Décompilation tests valides syntax---------";
for cas_de_test in src/test/deca/syntax/valid/*.deca
do
    test_decompile_valid $cas_de_test;
done
echo "-------Décompilation tests valides context---------";
for cas_de_test in src/test/deca/context/valid/*.deca
do
    test_decompile_valid $cas_de_test;
done
echo "-------Décompilation tests valides codegen---------";
for cas_de_test in src/test/deca/codegen/valid/*.deca
do
    test_decompile_valid $cas_de_test;
done