package com.codeboyq.AdminiMover;

import java.io.File;

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
    	File srcFile = new File ("/Users/astronauta/Documents/java_workspaces/projects/AdminiMover/src/test/resources/InputFolder/Box.pdf");
    	System.out.println("Ja" + srcFile.exists());
    	FileMover.moveFile(srcFile, "Hooplot Media BV", "20181112", "Tesla");
    }
}
