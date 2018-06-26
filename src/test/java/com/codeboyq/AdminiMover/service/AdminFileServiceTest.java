package com.codeboyq.AdminiMover.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import com.codeboyq.AdminiMover.AdminiMoverException;
import com.codeboyq.AdminiMover.common.Configuration;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AdminFileServiceTest 
    extends TestCase
{

    public AdminFileServiceTest( String testName ) {
        super( testName );
    }

    public static Test suite() {
        return new TestSuite(AdminFileServiceTest.class);
    }
    
	public void testMoveFile1() throws AdminiMoverException {
    	File inputFile = createTemporaryFile();
    	File movedFile = AdminFileService.moveFile(inputFile.getPath(), "Hooplot Media BV", "20181115", "Tesla");
    	
        Path relativePath = Paths.get(Configuration.instance().getRootDirectory()).relativize(movedFile.toPath());

        Assert.assertTrue(relativePath.getName(0).toString().equals("Hooplot Media BV"));
        Assert.assertTrue(relativePath.getName(1).toString().equals("2018"));
        Assert.assertTrue(relativePath.getName(2).toString().equals(Configuration.instance().getCategory()));
        Assert.assertTrue(relativePath.getName(3).toString().equals("Q4"));
        Assert.assertTrue(relativePath.getName(4).toString().equals("11 November"));
        Assert.assertTrue(relativePath.getName(5).toString().startsWith("20181115 Tesla"));

    	FileUtils.deleteQuietly(movedFile);
    }
	
	public void testMoveFile2() throws AdminiMoverException {
    	File inputFile = createTemporaryFile();
    	File movedFile = AdminFileService.moveFile(inputFile.getPath(), "Hooplot Holding BV", "20190823", "Good Music");
    	
        Path relativePath = Paths.get(Configuration.instance().getRootDirectory()).relativize(movedFile.toPath());

        Assert.assertTrue(relativePath.getName(0).toString().equals("Hooplot Holding BV"));
        Assert.assertTrue(relativePath.getName(1).toString().equals("2019"));
        Assert.assertTrue(relativePath.getName(2).toString().equals(Configuration.instance().getCategory()));
        Assert.assertTrue(relativePath.getName(3).toString().equals("Q3"));
        Assert.assertTrue(relativePath.getName(4).toString().equals("08 Augustus"));
        Assert.assertTrue(relativePath.getName(5).toString().startsWith("20190823 Good Music"));

    	FileUtils.deleteQuietly(movedFile);
    }
	
	public void testMoveFileDuplicate() throws AdminiMoverException {
    	File inputFile1 = createTemporaryFile();
    	File movedFile1 = AdminFileService.moveFile(inputFile1.getPath(), "Hooplot Holding BV", "20180101", "Wilde Haren de Podcast");
    	File inputFile2 = createTemporaryFile();
    	File movedFile2 = AdminFileService.moveFile(inputFile2.getPath(), "Hooplot Holding BV", "20180101", "Wilde Haren de Podcast");
    	
        Path relativePath = Paths.get(Configuration.instance().getRootDirectory()).relativize(movedFile2.toPath());
        Assert.assertTrue(relativePath.getName(5).toString().startsWith("20180101 Wilde Haren de Podcast_1"));

    	FileUtils.deleteQuietly(movedFile1);
    	FileUtils.deleteQuietly(movedFile2);
    }
	
	public void testMoveFileInvalidCompanyName() throws AdminiMoverException {
		File inputFile = createTemporaryFile();
    	File movedFile = null;
    	boolean thrown = false;
    	try {
    		movedFile = AdminFileService.moveFile(inputFile.getPath(), "Not my Company", "20184015", "Corom");
		} catch (Exception e) {
			thrown = true;
		}   	
    	Assert.assertTrue(thrown);
    	FileUtils.deleteQuietly(movedFile);
    }
	
	public void testMoveFileInvalidDate() throws AdminiMoverException {
    	File inputFile = createTemporaryFile();
    	File movedFile = null;
    	boolean thrown = false;
    	try {
    		movedFile = AdminFileService.moveFile(inputFile.getPath(), "Hooplot Media BV", "20184015", "Corom");
		} catch (Exception e) {
			thrown = true;
		}   	
    	Assert.assertTrue(thrown);
    	FileUtils.deleteQuietly(movedFile);
    }
	
	private File createTemporaryFile() throws AdminiMoverException {
		try {
			return File.createTempFile("Invoice", ".pdf");
		} catch (IOException e) {
			throw new AdminiMoverException(e);
		}
	}

}
