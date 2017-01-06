#!/bin/sh
for i in ../deca/syntax/valid/provided/*.deca
do
echo ""
echo "-----------------------------------"
echo ""
echo "$i"

launchers/test_lex "$i"

done

for i in ../deca/syntax/invalid/provided/*.deca
do
echo ""
echo "-----------------------------------"
echo ""
echo "$i"

launchers/test_lex "$i"

done
