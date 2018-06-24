# AdminiMover

## Summary
This application takes a file through a MacOSX Automator app
and takes some extra user input to move the file to a new
location based on the user input. This application is pretty
specific, but helps a lot to improve my own company administration
workflow.

## Logic
MacOSX Automator App is used
When a pdf file is dragged on to this application
the Automator Application initiates a run.sh shell script with the filepath as input
The run.sh shell script asks for the following user input:
- MyCompany
- Datestring (yyyyMMdd format)
- Customer
The application calculates a filepath from this input like
[Root]/[MyCompany]/[Category]/<Year>/<Quarter>/<Month nr and name>/<Datestring> <Customer>.pdf
The 'root' and 'Category' are taken from the configuration file
The 'Year', 'Quarter' and 'Month nr and name' are calculated

## Configuration
adminimover-config.yml