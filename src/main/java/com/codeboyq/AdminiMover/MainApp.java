package com.codeboyq.AdminiMover;

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
 * args[1] = MyCompany (Holding or Werkmaatschappij)
 * args[2] = Invoice date in yyyyMMdd format
 * args[3] = Customer name
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
        	
        	System.out.println("AdminiMover");
        	logger.info("Input file: {}", filePath);

        	String myCompany = getUserInputFromList(Configuration.instance().getMyCompanies(), "Which Company?", scanner, false);
        	String dateString = getDateInput("Which date", scanner);
        	String customer = getUserInputFromList(Configuration.instance().getCustomers(), "Which Customer?", scanner, true);

        	AdminFileService.moveFile(filePath, myCompany, dateString, customer);
	
        }
 	
    	logger.exit();
    
    }
    
    private static String getUserInputFromList(List<String> list, String promptMessage, Scanner scanner, boolean freeInput) {
    	System.out.println(promptMessage);
    	for (int i = 0; i < list.size(); i++) {
    		System.out.println((i+1) + ". " + list.get(i));
    	}
    	System.out.print("Choose a number");
    	System.out.print(freeInput ? " or type in something: " : ": ");	
    	String input = scanner.next();
    	if (!StringUtils.isNumeric(input)&&freeInput) {
        	System.out.println("Choice: " + input + "\n");
    		return input;
    	} else if (StringUtils.isNumeric(input)&&Integer.parseInt(input)<=list.size()) {
    		String inputFromList = list.get(Integer.parseInt(input) - 1);
    		System.out.println("Choice: " + inputFromList + "\n");
    		return inputFromList;
    	} else {
    		System.out.println("Please make a valid choice! \n");
    		return getUserInputFromList(list, promptMessage, scanner, freeInput);
    	}
    	
    }
    
    private static String getDateInput(String promptMessage, Scanner scanner) {
    	System.out.println(promptMessage);
    	String input = scanner.next();
    	if (!AdminFileService.isValidAdminDate(input)) {
    		System.out.println("Please enter a valid yyyyMMdd date (e.g. 20180823)! \n");
    		return getDateInput(promptMessage, scanner);
    	}
    	System.out.println("Choice: " + input + "\n");
    	return input;
    }
  

}
