package com.dimensionless.ImageStream.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUtils {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		for (final File fileEntry : new File("/Users/santanu/Downloads/katy").listFiles()) {
	        System.out.println(fileEntry.getName());
	        fileEntry.renameTo(new File("/Users/santanu/Downloads/future/"+fileEntry.getName()));
	    }
		

	}

}
