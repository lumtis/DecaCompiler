#!/bin/sh
for i in ../deca/context/valid/provided/*.deca
do
echo ""
echo "-----------------------------------"
echo ""
echo "$i"

launchers/test_context "$i"

done

for i in ../deca/context/invalid/provided/*.deca
do
echo ""
echo "-----------------------------------"
echo ""
echo "$i"

launchers/test_context "$i"

done
