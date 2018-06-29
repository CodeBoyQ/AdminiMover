-- This Applescript is used in the AdminiMover Automator Service and is only here for reference
on run {input, parameters}

-- Get the directory of the script
tell application "Finder"
   set currentDir to POSIX path of (path to applications folder as text)
end tell

-- Run the Shell script with the Automator FilePath variable as parameter
tell application "Terminal"
    activate
	set filePath to (item 1 of input)
	do script "cd '" & currentDir & "/_AdminiMoverBinaries' && ./run.sh '" & filePath & "'"
end tell

end run