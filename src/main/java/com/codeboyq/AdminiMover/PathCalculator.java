package com.codeboyq.AdminiMover;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class PathCalculator {

    /**
     * Calculates the targetpath e.g. /Users/astronauta/Dropbox/admin zakelijk/Hooplot Holding BV/2018/1. Inkoop/Q1/01 Januari
     * 
     * @param myCompany
     * @param dateString
     * @return
     */
    public static Path calculatePath (String myCompany, LocalDate date) {
    	
    	return Paths.get(String.format("/Users/astronauta/Dropbox/admin zakelijk/%s/%s/1. Inkoop/%s/%s" 
    			,myCompany
    			,date.getYear()
    			,getQuarter(date)
    			,getMonthNumberAndName(date)));
    }

    
    private static String getMonthNumberAndName (LocalDate date) {
    	String maskedMonthNr = String.format("%02d", date.getMonthValue());
    	String dutchMonthName = date.getMonth().getDisplayName( TextStyle.FULL , new Locale.Builder().setLanguage("nl").build()) ;
    	String dutchMonthNameCapitalized = dutchMonthName.substring(0, 1).toUpperCase() + dutchMonthName.substring(1);
    	return maskedMonthNr + " " + dutchMonthNameCapitalized; //Output example: 07 Augustus
    }

    private static String getQuarter (LocalDate date) {
    	int quarter = ((date.getMonth().getValue() - 1) / 3);
    	String[] quarterKey = {"Q1", "Q2", "Q3", "Q4"};
    	return quarterKey[quarter];
    }
    
}
