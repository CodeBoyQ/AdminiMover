package com.codeboyq.AdminiMover;

import java.io.File;

import org.junit.Ignore;

public class MainAppTest
{

    @Ignore("Because this test triggers the Main method which asks for user input")
    public void testMainApp() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf");
        String[] args = {inputFile.getPath()};
        MainApp.main(args);
    }
}