package com.codeboyq.AdminiMover;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class AdminFileService {
	
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
    public static File moveFile(File srcFile, String myCompany, String dateString, String customer) throws Exception {
    	
    	//LOGGER.entry(srcFile, myCompany, dateString, customer);
    	
    	if (!srcFile.exists()) {
    		//TODO: Logger
    		throw new Exception("Please supply an existing file");
    	}
    	
    	if (srcFile.isDirectory()) {
    		//TODO: Logger
    		throw new Exception("Please supply a valid file. No directory");
    	}
    	
    	if (!FilenameUtils.getExtension(srcFile.getName()).equalsIgnoreCase("pdf")) {
    		//TODO: Logger
    		throw new Exception("Please supply a pdf");   		
    	}
    	
    	AdminPath adminPath = AdminPathFactory.getInstance(myCompany, dateString, customer);
    	IncomingInvoice invoice = new IncomingInvoice(dateString, customer);
    	File destFile = new File(adminPath.getFile(), invoice.getFilename());
    	destFile = getUniqueFilename(destFile);
    	FileUtils.moveFile(srcFile, destFile);

    	//System.out.println("Dest: " + destFile.getAbsolutePath());
    	
    	return destFile;
    	//LOGGER.exit(false);

    } 

    private static File getUniqueFilename(File file)
    {
        String baseName = FilenameUtils.getBaseName( file.getName() );
        String extension = FilenameUtils.getExtension( file.getName() );
        int counter = 1;
        while(file.exists())
        {
            file = new File( file.getParent(), baseName + "_" + (counter++) + "." + extension );
        }
        return file;
    }
    

}
