package com.codeboyq.AdminiMover;

import java.nio.file.Paths;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class PathCalculatorTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PathCalculatorTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PathCalculatorTest.class );
    }

    public void testCalculatePath() throws Exception {
    	assertTrue(PathCalculator.calculatePath("Hooplot Holding BV", DateUtil.convertToDate("20180217")).endsWith(Paths.get("Q1/02 Februari")));
    	assertTrue(PathCalculator.calculatePath("Hooplot Holding BV", DateUtil.convertToDate("20170717")).endsWith(Paths.get("Q3/07 Juli")));
    	assertTrue(PathCalculator.calculatePath("Hooplot Holding BV", DateUtil.convertToDate("20181217")).endsWith(Paths.get("Q4/12 December")));
    }
}
