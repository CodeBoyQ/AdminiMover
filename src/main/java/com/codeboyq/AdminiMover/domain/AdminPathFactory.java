package com.codeboyq.AdminiMover.domain;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

public class AdminPathFactory {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(AdminPathFactory.class);

	public static final String ROOT_DIRECTORY = "/Users/astronauta/Documents/java_workspaces/projects/AdminiMover/src/test/resources/RootFolder"; //TODO: Properties file + /Users/astronauta/Dropbox/admin zakelijk
	public static final String CATEGORY = "1. Inkoop";
	public static final List<String> COMPANY = Arrays.asList("Hooplot Holding BV","Hooplot Media BV"); //TODO: Enumerations

	public static AdminPath getInstance(String myCompany, String dateString, String customer) throws Exception {
		logger.entry(myCompany, dateString, customer);
		
		checkRootDirectory(ROOT_DIRECTORY);
		checkMyCompany(myCompany);
		LocalDate date = checkDateString(dateString);
		
		return logger.exit(new AdminPath(ROOT_DIRECTORY, 
				myCompany, 
				getYearPart(date), 
				CATEGORY, 
				getQuarterPart(date), 
				getMonthPart(date)));    	
	}
	
	private static void checkRootDirectory(String rootDirectory) throws Exception {
		logger.entry(rootDirectory);
		if (!new File(rootDirectory).exists()) {
			throw new Exception("Root directory " + ROOT_DIRECTORY + " does not exist!");
		}
		logger.exit();
	}
	
	private static void checkMyCompany(String myCompany) throws Exception {
		logger.entry(myCompany);
    	if (!COMPANY.contains(myCompany)) {
    		throw new Exception("Company name " + myCompany + " is invalid. Please use a valid Company name.");
    	}
    	logger.exit();
	}
	
    private static LocalDate checkDateString(String dateString) throws Exception {
    	logger.entry(dateString);
    	LocalDate date = null;
		try {
			DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
			date = LocalDate.parse(dateString, DATEFORMATTER);
		} catch (Exception e) {
			//TODO: LOGGER gebruiken
			System.out.println("Please use a valid yyyyMMdd dateString. " + dateString + " is not a valid String.");
			throw new Exception();
		}
        return logger.exit(date);
    }
    
    private static String getYearPart(LocalDate date) {
    	return String.valueOf(date.getYear());
    }
	
    private static String getMonthPart (LocalDate date) {
    	logger.entry(date);
    	String maskedMonthNr = String.format("%02d", date.getMonthValue());
    	String dutchMonthName = date.getMonth().getDisplayName( TextStyle.FULL , new Locale.Builder().setLanguage("nl").build()) ;
    	String dutchMonthNameCapitalized = dutchMonthName.substring(0, 1).toUpperCase() + dutchMonthName.substring(1);
    	return logger.exit(maskedMonthNr + " " + dutchMonthNameCapitalized); //Output example: 07 Augustus
    }

    private static String getQuarterPart (LocalDate date) {
    	logger.entry(date);
    	int quarter = date.get(IsoFields.QUARTER_OF_YEAR) - 1;
    	String[] quarterKey = {"Q1", "Q2", "Q3", "Q4"};
    	return logger.exit(quarterKey[quarter]);
    }
}
