package com.codeboyq.AdminiMover;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class FileServiceTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FileServiceTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FileServiceTest.class );
    }
    
	public void testMoveFileA() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf"); 
    	File movedFile = AdminFileService.moveFile(inputFile, "Hooplot Media BV", "20181115", "Tesla");
    	
        Path relativePath = Paths.get(AdminPathFactory.ROOT_DIRECTORY).relativize(movedFile.toPath());

        Assert.assertTrue(relativePath.getName(0).toString().equals("Hooplot Media BV"));
        Assert.assertTrue(relativePath.getName(1).toString().equals("2018"));
        Assert.assertTrue(relativePath.getName(2).toString().equals(AdminPathFactory.CATEGORY));
        Assert.assertTrue(relativePath.getName(3).toString().equals("Q4"));
        Assert.assertTrue(relativePath.getName(4).toString().equals("11 November"));
        Assert.assertTrue(relativePath.getName(5).toString().startsWith("20181115 Tesla"));

    	FileUtils.deleteQuietly(movedFile);
    }
	
	public void testMoveFileB() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf"); 
    	File movedFile = AdminFileService.moveFile(inputFile, "Hooplot Holding BV", "20190823", "Good Music");
    	
        Path relativePath = Paths.get(AdminPathFactory.ROOT_DIRECTORY).relativize(movedFile.toPath());

        Assert.assertTrue(relativePath.getName(0).toString().equals("Hooplot Holding BV"));
        Assert.assertTrue(relativePath.getName(1).toString().equals("2019"));
        Assert.assertTrue(relativePath.getName(2).toString().equals(AdminPathFactory.CATEGORY));
        Assert.assertTrue(relativePath.getName(3).toString().equals("Q3"));
        Assert.assertTrue(relativePath.getName(4).toString().equals("08 Augustus"));
        Assert.assertTrue(relativePath.getName(5).toString().startsWith("20190823 Good Music"));

    	FileUtils.deleteQuietly(movedFile);
    }
	
	public void testMoveFileDuplicate() throws Exception {
    	File inputFile1 = File.createTempFile("Invoice", ".pdf"); 
    	File movedFile1 = AdminFileService.moveFile(inputFile1, "Hooplot Holding BV", "20180101", "Wilde Haren de Podcast");
    	File inputFile2 = File.createTempFile("Invoice", ".pdf"); 
    	File movedFile2 = AdminFileService.moveFile(inputFile2, "Hooplot Holding BV", "20180101", "Wilde Haren de Podcast");
    	
        Path relativePath = Paths.get(AdminPathFactory.ROOT_DIRECTORY).relativize(movedFile2.toPath());
        Assert.assertTrue(relativePath.getName(5).toString().startsWith("20180101 Wilde Haren de Podcast_1"));

    	FileUtils.deleteQuietly(movedFile1);
    	FileUtils.deleteQuietly(movedFile2);
    }
	
	public void testMoveFileInvalidCompanyName() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf"); 
    	File movedFile = null;
    	boolean thrown = false;
    	try {
    		movedFile = AdminFileService.moveFile(inputFile, "Not my Company", "20184015", "Corom");
		} catch (Exception e) {
			thrown = true;
		}   	
    	Assert.assertTrue(thrown);
    	FileUtils.deleteQuietly(movedFile);
    }
	
	public void testMoveFileInvalidDate() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf"); 
    	File movedFile = null;
    	boolean thrown = false;
    	try {
    		movedFile = AdminFileService.moveFile(inputFile, "Hooplot Media BV", "20184015", "Corom");
		} catch (Exception e) {
			thrown = true;
		}   	
    	Assert.assertTrue(thrown);

    	FileUtils.deleteQuietly(movedFile);
    }

}
