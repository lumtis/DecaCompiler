#!/bin/sh
for i in ../deca/syntax/valid/provided/*.deca
do
echo ""
echo "-----------------------------------"
echo ""
echo "$i"

launchers/test_synt "$i"

done

for i in ../deca/syntax/invalid/provided/*.deca
do
echo ""
echo "-----------------------------------"
echo ""
echo "$i"

launchers/test_synt "$i"

done
