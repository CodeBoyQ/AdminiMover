package com.codeboyq.AdminiMover.domain;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Locale;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;

import com.codeboyq.AdminiMover.AdminiMoverException;
import com.codeboyq.AdminiMover.common.Configuration;
import com.codeboyq.AdminiMover.service.AdminFileService;

public class AdminPathFactory {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(AdminPathFactory.class);

	private static AdminPathFactory instance = null;
	
	private Configuration config = null;
	
	protected AdminPathFactory() throws IOException {
		config = Configuration.instance();
	}
	
    public static AdminPathFactory instance() throws IOException {
        if (instance == null) {
            instance = new AdminPathFactory();
        }
        return instance;
    }

	public AdminPath getAdminPath(String myCompany, String dateString, String customer) {
		logger.entry(myCompany, dateString, customer);
		
		checkRootDirectory(config.getRootDirectory());
		checkMyCompany(myCompany);
		LocalDate date = AdminFileService.getDate(dateString); 
		
		return logger.exit(new AdminPath(config.getRootDirectory(), 
				myCompany, 
				getYearPart(date), 
				config.getCategory(), 
				getQuarterPart(date), 
				getMonthPart(date)));    	
	}
	
	private void checkRootDirectory(String rootDirectory) throws AdminiMoverException {
		logger.entry(rootDirectory);
		if (!new File(rootDirectory).exists()) {
			throw new AdminiMoverException("Root directory " + rootDirectory + " does not exist!");
		}
		logger.exit();
	}
	
	private void checkMyCompany(String myCompany) throws AdminiMoverException {
		logger.entry(myCompany);
    	if (!config.getMyCompanies().contains(myCompany)) {
    		throw new AdminiMoverException("Company name " + myCompany + " is invalid. Please use a valid Company name.");
    	}
    	logger.exit();
	}
	
    private String getYearPart(LocalDate date) {
    	return String.valueOf(date.getYear());
    }
	
    private String getMonthPart (LocalDate date) {
    	logger.entry(date);
    	String maskedMonthNr = String.format("%02d", date.getMonthValue());
    	String dutchMonthName = date.getMonth().getDisplayName( TextStyle.FULL , new Locale.Builder().setLanguage("nl").build()) ;
    	String dutchMonthNameCapitalized = dutchMonthName.substring(0, 1).toUpperCase() + dutchMonthName.substring(1);
    	return logger.exit(maskedMonthNr + " " + dutchMonthNameCapitalized); //Output example: 07 Augustus
    }

    private String getQuarterPart (LocalDate date) {
    	logger.entry(date);
    	int quarter = date.get(IsoFields.QUARTER_OF_YEAR) - 1;
    	String[] quarterKey = {"Q1", "Q2", "Q3", "Q4"};
    	return logger.exit(quarterKey[quarter]);
    }
    
}
