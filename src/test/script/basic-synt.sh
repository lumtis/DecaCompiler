#! /bin/sh

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

# exemple de définition d'une fonction
test_synt_invalide () {
    # $1 = premier argument.
    if test_synt "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:"
    then
        echo -e "\033[32m$1 : PASS.\033[0m"
    else
        echo -e "\033[31m$1 : ERROR.\033[0m"
        test_synt "$1";
        exit 1
    fi
}    


test_synt_valid() {

if test_synt "$1" 2>&1 | grep -q -e ':[0-9][0-9]*:'
then
        echo -e "\033[31m$1 : ERROR.\033[0m"
        test_synt "$1" 2>&1;
        exit 1
else
    if test_synt "$1" 2>&1 | grep -q -e 'Exception in thread'
    then
        echo -e "\033[31m$1 : ERROR.\033[0m"
        test_synt "$1" 2>&1;
        exit 1
    else
        echo -e "\033[32m$1 : PASS.\033[0m"
    fi
fi
}
echo "---Début tests invalides---"
for cas_de_test in src/test/deca/syntax/invalid/*.deca
do
    test_synt_invalide "$cas_de_test"
done
echo ""
echo "---Debut tests valides---"
for cas_de_test in src/test/deca/syntax/valid/*.deca
do
    test_synt_valid "$cas_de_test"
done

echo "OK !"
