package com.codeboyq.AdminiMover;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class AdminPathFactory {

	public static final String ROOT_DIRECTORY = "/Users/astronauta/Documents/java_workspaces/projects/AdminiMover/src/test/resources/RootFolder"; //TODO: Properties file + /Users/astronauta/Dropbox/admin zakelijk
	public static final String CATEGORY = "1. Inkoop";
	public static final List<String> COMPANY = Arrays.asList("Hooplot Holding BV","Hooplot Media BV"); //TODO: Enumerations

	public static AdminPath getInstance(String myCompany, String dateString, String customer) throws Exception {
		
		checkRootDirectory(ROOT_DIRECTORY);
		checkMyCompany(myCompany);
		LocalDate date = checkDateString(dateString);
		
		return new AdminPath(ROOT_DIRECTORY, 
				myCompany, 
				getYearPart(date), 
				CATEGORY, 
				getQuarterPart(date), 
				getMonthPart(date));
    	
	}
	
	private static void checkRootDirectory(String rootDirectory) throws Exception {
		if (!new File(rootDirectory).exists()) {
			throw new Exception("Root directory " + ROOT_DIRECTORY + " does not exist!");
		}
	}
	
	private static void checkMyCompany(String myCompany) throws Exception {
    	if (!COMPANY.contains(myCompany)) {
    		throw new Exception("Company name " + myCompany + " is invalid. Please use a valid Company name.");
    	}
	}
	
    private static LocalDate checkDateString(String dateString) throws Exception {
    	LocalDate date = null;
		try {
			DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
			date = LocalDate.parse(dateString, DATEFORMATTER);
		} catch (Exception e) {
			//TODO: LOGGER gebruiken
			System.out.println("Please use a valid yyyyMMdd dateString. " + dateString + " is not a valid String.");
			throw new Exception();
		}
        return date;
    }
    
    private static String getYearPart(LocalDate date) {
    	return String.valueOf(date.getYear());
    }
	
    private static String getMonthPart (LocalDate date) {
    	String maskedMonthNr = String.format("%02d", date.getMonthValue());
    	String dutchMonthName = date.getMonth().getDisplayName( TextStyle.FULL , new Locale.Builder().setLanguage("nl").build()) ;
    	String dutchMonthNameCapitalized = dutchMonthName.substring(0, 1).toUpperCase() + dutchMonthName.substring(1);
    	return maskedMonthNr + " " + dutchMonthNameCapitalized; //Output example: 07 Augustus
    }

    private static String getQuarterPart (LocalDate date) {
    	int quarter = date.get(IsoFields.QUARTER_OF_YEAR) - 1;
    	String[] quarterKey = {"Q1", "Q2", "Q3", "Q4"};
    	return quarterKey[quarter];
    }
}
