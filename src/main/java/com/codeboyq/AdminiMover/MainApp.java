package com.codeboyq.AdminiMover;

import java.io.File;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.codeboyq.AdminiMover.service.AdminFileService;


/**
 * Main AdminiMover Application
 * 
 * This application takes a file through a MacOSX Automator app
 * and takes some extra user input to move the file to a new
 * location based on the user input. This application is pretty
 * specific, but helps a lot to improve my own company administration
 * workflow
 * 
 * Arguments:
 * args[0] = File path of the source file
 * args[1] = MyCompany (Holding or Werkmaatschappij)
 * args[2] = Invoice date in yyyyMMdd format
 * args[3] = Customer name
 *
 */
public class MainApp {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(MainApp.class);

    public static void main( String[] args ) throws Exception {
    	logger.entry(args[0], args[1], args[2], args[3]);
    	
    	if (args.length < 4) {
    		throw new Exception("Please give valid arguments: 1. Source filePath 2. My Company 3. Datestring yyyyMMdd 4. Customer");
    	}
    	
    	logger.info("Starting move of file: {}", args[0]);
    	File destFile = AdminFileService.moveFile(
    			args[0], 
    			args[1], 
    			args[2],
    			args[3]);
    	
    	logger.info("File moved to {}", destFile.getAbsolutePath());
    	logger.exit();
    
    }
    

}
