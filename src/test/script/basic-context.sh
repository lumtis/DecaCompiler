#! /bin/sh

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

test_context_invalid() {
    line=$(cat "$1" | grep -e "//ERROR :[0-9]*:" | tail -c +9);
    if test_context "$1" 2>&1 | grep -q -e "$1$line"
    then
        echo "$1 : PASS."
    else
        echo "$1 : ERROR."
        echo "Expected error at line $line";
        echo "Error raised :";
        test_context "$1" 2>&1;
        exit 1
    fi
}

test_context_valid() {
    if test_context "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:[0-9][0-9]*"
    then
        echo "$1 : ERROR."
        test_context "$1" 2>&1;
        exit 1
    else
        echo "$1 : PASS."
    fi
}

echo "---Début tests invalides---"
for cas_de_test in src/test/deca/context/invalid/*.deca
do
    test_context_invalid "$cas_de_test"
done
echo ""
echo "---Debut tests valides---"
for cas_de_test in src/test/deca/context/valid/*.deca
do
    test_context_invalid "$cas_de_test"
done

