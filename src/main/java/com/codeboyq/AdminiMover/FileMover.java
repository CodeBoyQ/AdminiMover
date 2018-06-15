package com.codeboyq.AdminiMover;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class FileMover {

	/**
	 * Moves the inputFile to the right directory calculated by the parameters myCompany and date
	 * and also give the moved file a new name: "[date] [customer]"
	 * 
	 * @param inputFile
	 * @param myCompany
	 * @param date
	 * @param customer
	 */
    public static void moveFile(File inputFile, String myCompany, String dateString, String customer) {
    	//TODO: Use an enumeration for myCompany
    	
    	LocalDate date = converToDate(dateString);
    	

    }  

}
