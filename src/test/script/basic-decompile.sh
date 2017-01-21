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
        echo -e "\033[32m$1 : PASS.\033[0m"
    else
        echo -e "\033[31m$1 : ERROR.\033[0m"
    fi
}
echo "-------Décompilation tests valides context---------";
for cas_de_test in src/test/deca/context/valid/*.deca
do
    test_decompile_valid $cas_de_test;
done
echo "-------Décompilation tests valides codegen---------";
for cas_de_test in src/test/deca/codegen/valid/Ok/*.deca
do
    test_decompile_valid $cas_de_test;
done