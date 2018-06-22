package com.codeboyq.AdminiMover.domain;

import static java.lang.String.format;

import java.util.List;

public final class Configuration { 
    private String rootDirectory;
    private String category;
    private List<String> myCompanies;

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

	@Override
    public String toString() {
        return new StringBuilder()
            .append( format( "Root Directory: %s\n", rootDirectory ) )
            .append( format( "Category: %s\n", category ) )
            .append( format( "MyCompanies: %s\n", myCompanies ) )
            .toString();
    }
}