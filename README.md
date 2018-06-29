# AdminiMover

## Summary
This application takes a file through a MacOSX Automator app
and takes some extra user input to move the file to a new
location based on the user input. This application is pretty
specific, but helps a lot to improve my own company administration
workflow.

## Requirements

macOS High Sierra or later

## Installation

### Application

No installation required. Copy the `AdminiMoverApp` folder anywhere and 
drag a pdf file onto the AdminiMover.app


### Service

Go to the `AdminiMoverSevice` folder
Copy the `AdminiMoverService.workflow` to `/Users/<User>/Library/Services` (Shift + Command + . to see hidden files)
Copy the `_AdminiMoverBinaries` folder to `/Applications`

Open `Finder`
In the upper menu, choose: `Finder > Services > Services Preferences`
Choose `Services` in the left menu and make sure the AdminiMoverService under Files and Folders is activated

Now you can right-click on a pdf file and choose AdminiMoverService in the context menu


## Configuration
adminimover-config.yml

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