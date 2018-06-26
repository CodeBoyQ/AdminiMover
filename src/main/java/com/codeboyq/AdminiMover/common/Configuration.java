package com.codeboyq.AdminiMover.common;

import static java.lang.String.format;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.ext.XLogger;
import org.slf4j.ext.XLoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.codeboyq.AdminiMover.AdminiMoverException;
import com.codeboyq.AdminiMover.MainApp;
import com.codeboyq.AdminiMover.domain.AdminPathFactory;

public final class Configuration {
	
	private static final XLogger logger = XLoggerFactory.getXLogger(Configuration.class);
    
	private static Configuration instance = null;
    
	private String rootDirectory;
    private String category;
    private List<String> myCompanies;
    private List<String> customers;
    
    private Configuration() {	
    }
    
    public static Configuration instance() throws AdminiMoverException {
    	if (instance == null) {
    		instance = readConfiguration();
    		logger.debug(instance.toString());
    	}
    	return instance;
    }
    
    public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<String> getMyCompanies() {
		return myCompanies;
	}

	public void setMyCompanies(List<String> myCompanies) {
		this.myCompanies = myCompanies;
	}
	
	public List<String> getCustomers() {
		return customers;
	}

	public void setCustomers(List<String> customers) {
		this.customers = customers;
	}

	@Override
    public String toString() {
        return new StringBuilder()
    		.append( "Read configuration: \n")
            .append( format( "Root Directory: %s\n", rootDirectory ) )
            .append( format( "Category: %s\n", category ) )
            .append( format( "MyCompanies: %s\n", myCompanies ) )
            .append( format( "Customers: %s\n", customers ) )
            .toString();
    }
	
    private static Configuration readConfiguration() throws AdminiMoverException {
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
			throw new AdminiMoverException("Unable to read config file", e);
		} finally {
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new AdminiMoverException(e);
				}
			}
		}
    	
    }
}