package com.codeboyq.AdminiMover;

import java.io.File;

import org.junit.Test;

public class MainAppTest
{

    @Test
    public void testMainApp() throws Exception {
    	File inputFile = File.createTempFile("Invoice", ".pdf");
        String[] args = {inputFile.getPath()};
        MainApp.main(args);
    }
}