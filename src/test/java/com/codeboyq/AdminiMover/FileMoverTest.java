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
public class FileMoverTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public FileMoverTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( FileMoverTest.class );
    }
    
	public void testMoveFile() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf"); 
    	System.out.println("Ja" + inputFile.exists());
    	File movedFile = FileMover.moveFile(inputFile, "Hooplot Media BV", "20181115", "Tesla");
    	System.out.println(movedFile.getPath());
    	
        Path relativePath = Paths.get(PathCalculator.ROOT_DIRECTORY).relativize(movedFile.toPath());

        Assert.assertTrue(relativePath.getName(0).toString().equals("Hooplot Media BV"));
        Assert.assertTrue(relativePath.getName(1).toString().equals("2018"));
        Assert.assertTrue(relativePath.getName(2).toString().equals(PathCalculator.CATEGORY));
        Assert.assertTrue(relativePath.getName(3).toString().equals("Q4"));
        Assert.assertTrue(relativePath.getName(4).toString().equals("11 November"));
        Assert.assertTrue(relativePath.getName(5).toString().startsWith("20181115 Tesla"));

    	FileUtils.deleteQuietly(movedFile);
    }

}
