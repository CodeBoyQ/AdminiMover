package com.codeboyq.AdminiMover;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.codeboyq.AdminiMover.common.Configuration;
import com.codeboyq.AdminiMover.service.AdminFileService;


/**
 * Main AdminiMover Application
 * 
 * This application takes a file through a MacOSX Automator app
 * and takes some extra user input to move the file to a new
 * location based on the user input. This application is pretty
 * specific, but helps a lot to improve my own company administration
 * workflow
 * 
 * Arguments:
 * args[0] = File path of the source file

 *
 */
public class MainApp {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(MainApp.class);

    public static void main( String[] args ) throws AdminiMoverException {
    	logger.entry(args[0]);
    	
    	if (args.length != 1) {
    		throw new AdminiMoverException("Please give only one argument: filePath");
    	}
    	
    	String filePath = args[0];
    	
        try(Scanner scanner = new Scanner(System.in)){
        	
        	System.out.println("*****************************");
        	System.out.println("* AdminiMover to the rescue *");
        	System.out.println("*****************************");

        	logger.debug("Input file: {}", filePath);

        	String myCompany = getUserInputFromList(Configuration.instance().getMyCompanies(), "Which Company?", scanner, 2, false);
        	String dateString = getDateInput("Which date? (Default is today)", scanner, true);
        	String customer = getUserInputFromList(Configuration.instance().getCustomers(), "Which Customer?", scanner, 1, true);
        	System.out.println("File moved to: " + AdminFileService.moveFile(filePath, myCompany, dateString, customer));
	
        }
 	
    	logger.exit();
    
    }
    
    private static String getUserInputFromList(List<String> list, String promptMessage, Scanner scanner, int defaultChoice, boolean freeInput) {
    	System.out.println(promptMessage);
    	
    	if (defaultChoice<1||defaultChoice>list.size()) {
    		defaultChoice = 1;
    	}
    	for (int i = 0; i < list.size(); i++) {
    		System.out.println((i+1) + ". " + list.get(i) + " " + ((i+1)==defaultChoice ? " (default)" : ""));
    	}
    	System.out.print("Choose a number");
    	System.out.print(freeInput ? " or type in something: " : ": ");	
    	String input = scanner.nextLine();
    	if (!StringUtils.isNumeric(input)&&freeInput) {
        	System.out.println("Choice: " + input + "\n");
    		return input;
    	} else if (StringUtils.isNumeric(input)&&Integer.parseInt(input)<=list.size()) {
    		String inputFromList = list.get(Integer.parseInt(input) - 1);
    		System.out.println("Choice: " + inputFromList + "\n");
    		return inputFromList;
    	} else {
    		String inputFromList = list.get(defaultChoice - 1);
    		System.out.println("Default choice (" + defaultChoice + "): " + inputFromList + "\n");
    		return inputFromList;
    	}
    	
    }
    
    private static String getDateInput(String promptMessage, Scanner scanner, boolean defaultDateToday) {
    	System.out.println(promptMessage);
    	String input = scanner.nextLine();
    	if (!AdminFileService.isValidAdminDate(input)) {
    		if (defaultDateToday) {
    			input = AdminFileService.getDateString(LocalDate.now());
    		} else {
        		System.out.println("Please enter a valid yyyyMMdd date (e.g. 20180823)! \n");
        		return getDateInput(promptMessage, scanner, false);    			
    		}
    	}
    	System.out.println("Datestring: " + input + "\n");
    	return input;
    }
  

}
