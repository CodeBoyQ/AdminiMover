#!/bin/bash

# AdminiMover
# This script takes a filePath as parameter
# Takes some additional user input arguments and runs the AdminiMover application

clear 

echo "AminiMover"
Echo "Input file: $1"
echo ""
echo "Which company?"
echo "1. Hooplot BV"
echo "2. Hooplot Media (default)"
read inputMyCompany

if [ "$inputMyCompany" = "1" ]
then
    set myCompany = "Hooplot Holding BV"
else
    set myCompany = "Hooplot Media BV"
fi

echo ""
echo "Which date? e.g. 20180620"
read inputDate

echo ""
echo "Which customer?"
echo "Flexwebhosting"
echo "MediaMarkt"
echo "NSBusiness"
echo "Parkmobile"
echo "MKBBrandstof"
echo "Amazon"
echo "Ziggo"
echo "CJIB"
echo "123Inkt"
echo "ANWB"
echo "Restaurant"
echo "Crashplan"
echo "DropBox"
echo "Finovion"

read inputCustomer

cd ${0%/*}
java -jar "AdminiMover-0.0.1-SNAPSHOT-jar-with-dependencies.jar" "$1" "$myCompany" "$inputDate" "$inputCustomer"