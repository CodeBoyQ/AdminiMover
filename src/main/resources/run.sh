#!/bin/bash

# AdminiMover
# This script takes a filePath as parameter and runs the AdminiMover application

clear 

cd ${0%/*}
java -jar "adminiMoverApp.jar" "$1"