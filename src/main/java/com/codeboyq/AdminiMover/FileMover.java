package com.codeboyq.AdminiMover;

import java.io.File;
import java.nio.file.Path;
import java.time.LocalDate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class FileMover {
	
	//final static Logger LOGGER = LogManager.getLogger(FileMover.class.getName());
	
	/**
	 * Moves the srcFile to the right directory calculated by the parameters myCompany and date
	 * and also give the moved file a new name: "[date] [customer]"
	 * 
	 * @param inputFile
	 * @param myCompany
	 * @param date
	 * @param customer
	 * @throws Exception 
	 */
    public static void moveFile(File srcFile, String myCompany, String dateString, String customer) throws Exception {
    	
    	//LOGGER.entry(srcFile, myCompany, dateString, customer);
    	
    	if (!srcFile.exists() || srcFile.isDirectory()) {
    		//TODO: Logger
    		throw new Exception("Please supply a valid file");
    	}
    	
    	String fileExtension = FilenameUtils.getExtension(srcFile.getName());
    	
    	if (!fileExtension.equalsIgnoreCase("pdf")) {
    		//TODO: Logger
    		throw new Exception("Please supply a pdf");   		
    	}
    	
    	LocalDate date = DateUtil.convertToDate(dateString);
    	Path destinationPath = PathCalculator.calculatePath(myCompany, date);
    	File destFile = new File(destinationPath.toFile(), dateString + " " + customer + "." + fileExtension);
    	
    	System.out.println("Dest: " + destFile.getAbsolutePath());
    	FileUtils.moveFile(srcFile, destFile);
    	
    	FileUtils.moveFile(destFile, srcFile);
    	
    	//LOGGER.exit(false);

    } 
    

}
