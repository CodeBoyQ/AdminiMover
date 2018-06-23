package com.codeboyq.AdminiMover.service;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

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
	 * @throws Exception 
	 */
    public static File moveFile(String srcFilePath, String myCompany, String dateString, String customer) throws Exception {	
    	logger.entry(srcFilePath, myCompany, dateString, customer);
    	
    	File srcFile = new File (srcFilePath);
    	if (!srcFile.exists()) {
    		logger.error("Please supply an existing file");
    		throw new Exception("Please supply an existing file");
    	}
    	
    	if (srcFile.isDirectory()) {
    		logger.error("Please supply a valid file. No directory");
    		throw new Exception("Please supply a valid file. No directory");
    	}
    	
    	if (!FilenameUtils.getExtension(srcFile.getName()).equalsIgnoreCase("pdf")) {
    		logger.error("Please supply a pdf");
    		throw new Exception("Please supply a pdf");   		
    	}
    
    	AdminPath adminPath = AdminPathFactory.instance().getAdminPath(myCompany, dateString, customer);
    	IncomingInvoice invoice = new IncomingInvoice(dateString, customer);
    	File destFile = new File(adminPath.getFile(), invoice.getFilename());
    	destFile = getUniqueFilename(destFile);
    	FileUtils.moveFile(srcFile, destFile);
    	logger.info("File moved to {}", destFile.getAbsolutePath());
    	return logger.exit(destFile);

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
