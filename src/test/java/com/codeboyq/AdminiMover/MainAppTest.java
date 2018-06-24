package com.codeboyq.AdminiMover;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MainAppTest extends TestCase
{

    public MainAppTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(MainAppTest.class);
    }

    public void testMainApp() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf");
        String[] args = {inputFile.getPath(), "Hooplot Media BV", "20190101", "Santigold"};
        MainApp.main(args);
    }
}
