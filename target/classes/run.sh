#!/bin/bash

# AdminiMover
# This script takes a filePath as parameter
# Takes some additional user input arguments and runs the AdminiMover application

clear 

echo "AminiMover"
echo "Input file: $1"
inputFile="$1"

echo "Which company?"
echo "1. Hooplot BV"
echo "2. Hooplot Media (default)"
read inputMyCompany
if [ "$inputMyCompany" = "1" ]
then
    myCompany="Hooplot Holding BV"
else
    myCompany="Hooplot Media BV"
fi
echo "Company: $myCompany" 
echo ""

echo "Which date? e.g. 20180620"
read inputDate
echo "Date: $inputDate"
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
echo "Customer: $inputCustomer"

cd ${0%/*}
java -jar "AdminiMover-0.0.1-SNAPSHOT-jar-with-dependencies.jar" "$inputFile" "$myCompany" "$inputDate" "$inputCustomer"