package com.codeboyq.AdminiMover;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    //TODO: Good exception handleling
    public static LocalDate converToDate(String dateString) throws Exception {
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
}
