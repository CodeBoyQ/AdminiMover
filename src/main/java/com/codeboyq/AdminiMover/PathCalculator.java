package com.codeboyq.AdminiMover;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PathCalculator {

	public static final String ROOT_DIRECTORY = "/Users/astronauta/Documents/java_workspaces/projects/AdminiMover/src/test/resources/RootFolder"; //TODO: Properties file + /Users/astronauta/Dropbox/admin zakelijk
	public static final String CATEGORY = "1. Inkoop";
	public static final List<String> COMPANY = Arrays.asList("Hooplot Holding BV","Hooplot Media BV"); //TODO: Enumerations

    /**
     * Calculates the targetpath e.g. /Users/astronauta/Dropbox/admin zakelijk/Hooplot Holding BV/2018/1. Inkoop/Q1/01 Januari
     * 
     * @param myCompany
     * @param dateString
     * @return
     * @throws Exception 
     */
    public static Path calculatePath (String myCompany, LocalDate date) throws Exception {

    	if (!COMPANY.contains(myCompany)) {
    		throw new Exception("Company name " + myCompany + " is invalid. Please use a valid Company name.");
    	}
    	
    	return Paths.get(
    			ROOT_DIRECTORY
    			,myCompany
    			,String.valueOf(date.getYear())
    			,CATEGORY
    			,getQuarter(date)
    			,getMonthNumberAndName(date));
    }
    
    private static String getMonthNumberAndName (LocalDate date) {
    	String maskedMonthNr = String.format("%02d", date.getMonthValue());
    	String dutchMonthName = date.getMonth().getDisplayName( TextStyle.FULL , new Locale.Builder().setLanguage("nl").build()) ;
    	String dutchMonthNameCapitalized = dutchMonthName.substring(0, 1).toUpperCase() + dutchMonthName.substring(1);
    	return maskedMonthNr + " " + dutchMonthNameCapitalized; //Output example: 07 Augustus
    }

    private static String getQuarter (LocalDate date) {
    	int quarter = date.get(IsoFields.QUARTER_OF_YEAR) - 1;
    	String[] quarterKey = {"Q1", "Q2", "Q3", "Q4"};
    	return quarterKey[quarter];
    }
    
}
