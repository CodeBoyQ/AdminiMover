package com.codeboyq.AdminiMover.domain;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AdminPath {

	String baseDirectory;
	String myCompanyPart;
	String yearPart;
	String categoryPart;
	String quarterPart;
	String monthPart;

	public AdminPath(String baseDirectory, String myCompanyPart, String yearPart, String categoryPart,
		String quarterPart, String monthPart) {
		this.baseDirectory = baseDirectory;
		this.myCompanyPart = myCompanyPart;
		this.yearPart = yearPart;
		this.categoryPart = categoryPart;
		this.quarterPart = quarterPart;
		this.monthPart = monthPart;
	}

	public Path getFullPath() {
		return Paths.get(baseDirectory, myCompanyPart, yearPart, categoryPart, quarterPart, monthPart);
	}

	public Path getRelativePath() {
		return Paths.get(myCompanyPart, yearPart, categoryPart, quarterPart, monthPart);
	}

	public Path getBasePath() {
		return Paths.get(baseDirectory);
	}

	public File getFile() {
		return getFullPath().toFile();
	}

}
