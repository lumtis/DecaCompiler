#! /bin/sh

# Parcourt les fichiers tests dans codegen/valid et codegen/invalid.
# Il faut faire en sorte que le programme renvoie "OK" pour le fichiers valides et aucune expression
# avec "OK" pour les fichiers invalides.
# On peut facilement le faire avec des conditions dans le programme deca.
cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:./src/main/bin:"$PATH"
echo "-----Test des fichiers valides------";
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
echo "";
echo "----------Test des fichiers invalides---------";
for cas_de_test in src/test/deca/codegen/invalid/*.deca
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
            echo "$cas_de_test : ERROR."
        else
            echo "$cas_de_test : PASS.";

        fi
    fi

done

# On parcourt les fichiers dans interactif, on lit dans le fichier l'input.
# L'input se trouve sur la ligne //INPUT [0-9]*
# Il faut mettre un seul espace après INPUT et aucun après sa valeur.
# Puis on exécute le code assembleur généré en lui fournissant l'input lu.
echo "";
echo "---------Test des fichiers interactifs-----------";
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

