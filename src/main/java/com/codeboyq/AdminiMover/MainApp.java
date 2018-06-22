package com.codeboyq.AdminiMover;

import java.io.File;

/**
 * Hello world!
 *
 */
public class MainApp 
{
    public static void main( String[] args ) throws Exception
    {
    	
    	if (args.length < 4) {
    		throw new Exception("Please give valid arguments: 1. Source filePath 2. My Company 3. Datestring yyyyMMdd 4. Customer");
    	}
    	
    	String srcFilePath = args[0];
    	String myCompany = args[1];
    	String dateString = args[2];
    	String customers = args[3];
    	
    	File destFile = AdminFileService.moveFile(new File(srcFilePath), myCompany, dateString, customers);
    	System.out.println("Dest: " + destFile.getAbsolutePath());

    	
        
    }
    

}
