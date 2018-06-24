package com.codeboyq.AdminiMover.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.List;
import java.util.Locale;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.yaml.snakeyaml.Yaml;

public class AdminPathFactory {
	
	private final XLogger logger = XLoggerFactory.getXLogger(AdminPathFactory.class);

	static private AdminPathFactory instance = null;
	public static String ROOT_DIRECTORY;
	public static String CATEGORY;
	public static List<String> COMPANY;
	
	protected AdminPathFactory() throws IOException {
		logger.entry();
        Configuration config = readConfiguration();
        ROOT_DIRECTORY = config.getRootDirectory();
        CATEGORY = config.getCategory();
        COMPANY = config.getMyCompanies();
        logger.debug(config.toString());
        logger.exit();
	}
	
    public static AdminPathFactory instance() throws IOException {
        if (instance == null) {
            instance = new AdminPathFactory();
        }
        return instance;
    }

	public AdminPath getAdminPath(String myCompany, String dateString, String customer) throws Exception {
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
	
	private void checkRootDirectory(String rootDirectory) throws Exception {
		logger.entry(rootDirectory);
		if (!new File(rootDirectory).exists()) {
			throw new Exception("Root directory " + ROOT_DIRECTORY + " does not exist!");
		}
		logger.exit();
	}
	
	private void checkMyCompany(String myCompany) throws Exception {
		logger.entry(myCompany);
    	if (!COMPANY.contains(myCompany)) {
    		throw new Exception("Company name " + myCompany + " is invalid. Please use a valid Company name.");
    	}
    	logger.exit();
	}
	
    private LocalDate checkDateString(String dateString) throws Exception {
    	logger.entry(dateString);
    	LocalDate date = null;
		try {
			DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
			date = LocalDate.parse(dateString, DATEFORMATTER);
		} catch (Exception e) {
			logger.error("Please use a valid yyyyMMdd dateString. {} is not a valid String.", dateString);
			throw new Exception();
		}
        return logger.exit(date);
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
    
    private Configuration readConfiguration() throws IOException {
		logger.entry();
		
		InputStream in = null;
        try {
        	//First try to read the configuration file from classpath (used when tests are ran)
        	in = AdminPathFactory.class.getClassLoader().getResourceAsStream("adminimover-config.yml");
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
