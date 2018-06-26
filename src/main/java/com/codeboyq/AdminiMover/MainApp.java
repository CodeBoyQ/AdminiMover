package com.codeboyq.AdminiMover;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.codeboyq.AdminiMover.common.Configuration;
import com.codeboyq.AdminiMover.domain.AdminPathFactory;
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

    public static void main( String[] args ) throws Exception {
    	logger.entry(args[0]);
    	
    	if (args.length != 1) {
    		throw new Exception("Please give only one argument: filePath");
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
    
    private static Configuration readConfiguration() throws IOException {
		logger.entry();
		
		InputStream in = null;
        try {
        	//First try to read the configuration file from classpath (used when tests are ran)
        	in = MainApp.class.getClassLoader().getResourceAsStream("adminimover-config.yml");
			if (in == null) {
				// Secondly try to read the configuration file directly from the filesystem (used when executable jar is ran)
				logger.info("Jar file not on classpath. Reading directly from the filesystem");
				File jarPath = new File(AdminPathFactory.class.getProtectionDomain().getCodeSource().getLocation().getPath());
				String propertiesPath = jarPath.getParentFile().getAbsolutePath();
				in = new FileInputStream(propertiesPath + "/adminimover-config.yml");
			}
			
			return logger.exit(new Yaml().loadAs(in, Configuration.class)); 
			
        } catch (IOException e) {
			logger.error("Unable to read config file");
			throw new IOException("Unable to read config file", e);
		} finally {
			if (in!=null) {
				in.close();
			}
		}
    	
    }

}
