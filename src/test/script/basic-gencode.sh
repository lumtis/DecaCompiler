#! /bin/sh

# Auteur : gl35
# Version initiale : 01/01/2017

# Encore un test simpliste. On compile un fichier (cond0.deca), on
# lance ima dessus, et on compare le résultat avec la valeur attendue.

# Ce genre d'approche est bien sûr généralisable, en conservant le
# résultat attendu dans un fichier pour chaque fichier source.
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"

for cas_de_test in src/test/deca/codegen/valid/*.deca
do
    nom_ass=${cas_de_test/.deca/.ass}
    rm -f $nom_ass 2>/dev/null;
    decac $cas_de_test || exit 1
    if [ ! -f $nom_ass ]; then
        echo "Fichier cond0.ass non généré."
        exit 1
     else
        resultat=$(ima "$nom_ass")
        if echo $resultat | grep -q -e "OK"
        then
            echo "$cas_de_test : PASS."
        else
            echo "$cas_de_test : ERROR.";

        fi
    fi

done

# On parcourt les fichiers dans interactif, on lit dans le fichier l'input.
# Puis on exécute le code assembleur généré en lui fournissant l'assembleur lu.
for cas_de_test in src/test/deca/codegen/valid/interactive/*.deca
do
    nom_ass=${cas_de_test/.deca/.ass}
    rm -f $nom_ass 2>/dev/null;
    decac $cas_de_test || exit 1
    if [ ! -f $nom_ass ]; then
        echo "Fichier cond0.ass non généré."
        exit 1
     else
        input=$(cat "$cas_de_test" | grep -e "//INPUT [0-9]*" | tail -c +9);
        resultat=$(echo "$input" | ima "$nom_ass");
        if echo $resultat | grep -q -e "OK"
        then
            echo "$cas_de_test : PASS."
        else
            echo "$cas_de_test : ERROR.";

        fi
    fi

done

