package com.codeboyq.AdminiMover.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.codeboyq.AdminiMover.AdminiMoverException;
import com.codeboyq.AdminiMover.domain.AdminPath;
import com.codeboyq.AdminiMover.domain.AdminPathFactory;
import com.codeboyq.AdminiMover.domain.IncomingInvoice;

public class AdminFileService {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(AdminFileService.class);

	/**
	 * Moves the srcFile to the right directory calculated by the parameters myCompany and date
	 * and also give the moved file a new name: "[date] [customer]"
	 * 
	 * @param inputFile
	 * @param myCompany
	 * @param date
	 * @param customer
	 * @throws AdminiMoverException 
	 */
    public static File moveFile(String srcFilePath, String myCompany, String dateString, String customer) throws AdminiMoverException {	
    	logger.entry(srcFilePath, myCompany, dateString, customer);
    	
    	File srcFile = new File (srcFilePath);
    	if (!srcFile.exists()) {
    		logger.error("Please supply an existing file");
    		throw new AdminiMoverException("Please supply an existing file");
    	}
    	
    	if (srcFile.isDirectory()) {
    		logger.error("Please supply a valid file. No directory");
    		throw new AdminiMoverException("Please supply a valid file. No directory");
    	}
    	
    	if (!FilenameUtils.getExtension(srcFile.getName()).equalsIgnoreCase("pdf")) {
    		logger.error("Please supply a pdf");
    		throw new AdminiMoverException("Please supply a pdf");   		
    	}
    
    	try {
			AdminPath adminPath = AdminPathFactory.instance().getAdminPath(myCompany, dateString, customer);
			IncomingInvoice invoice = new IncomingInvoice(dateString, customer);
			File destFile = new File(adminPath.getFile(), invoice.getFilename());
			destFile = getUniqueFilename(destFile);
			FileUtils.moveFile(srcFile, destFile);
			logger.info("File moved to {}", destFile.getAbsolutePath());
			return logger.exit(destFile);
		} catch (IOException e) {
			throw new AdminiMoverException(e);
		}

    } 
    
    public static boolean isValidAdminDate(String dateString) {
    	logger.entry(dateString);
		try {
			DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDate.parse(dateString, DATEFORMATTER);
			return logger.exit(true);
		} catch (Exception e) {
			return logger.exit(false);
		}
    }
    
    public static LocalDate getDate(String dateString) throws AdminiMoverException {
    	logger.entry(dateString);
    	LocalDate date = null;
		try {
			DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
			date = LocalDate.parse(dateString, DATEFORMATTER);
		} catch (Exception e) {
			logger.error("Please use a valid yyyyMMdd dateString. {} is not a valid String.", dateString);
			throw new AdminiMoverException("Please use a valid yyyyMMdd dateString. " + dateString + " is not a valid String.");
		}
        return logger.exit(date);    	
    }
    
    public static String getDateString(LocalDate date) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    	return date.format(formatter);
    }

    private static File getUniqueFilename(File file) {
    	logger.entry(file);
        String baseName = FilenameUtils.getBaseName( file.getName() );
        String extension = FilenameUtils.getExtension( file.getName() );
        int counter = 1;
        while(file.exists())
        {
            file = new File( file.getParent(), baseName + "_" + (counter++) + "." + extension );
        }
        return logger.exit(file);
    }
    

}
