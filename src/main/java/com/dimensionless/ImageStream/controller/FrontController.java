package com.dimensionless.ImageStream.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dimensionless.ImageStream.model.Greeting;
import com.dimensionless.ImageStream.model.HelloMessage;

@Controller
public class FrontController {
	
	static List<String> processedImageList = new ArrayList<String>();
	@MessageMapping("/hello")
    @SendTo("/topic/greetings")
	@ResponseBody
    public Greeting sendImageByte(HelloMessage message) throws Exception {
		Greeting greeting = new Greeting(message.getName());
		greeting.setBytes(scanFoler ("/Users/santanu/Downloads/katy"));
		return greeting;
    }
	
	public  byte[] extractBytes (String fileName) throws IOException {
	//	System.out.println(fileName);
		processedImageList.add(fileName);
		File fi = new File(fileName);
		byte[] fileContent = Files.readAllBytes(fi.toPath());
		return fileContent;
	}
	
	public byte[] scanFoler(final String folder) throws IOException {
	    for (final File fileEntry : new File(folder).listFiles()) {
	        if (fileEntry.isDirectory()) {
	        		return scanFoler(fileEntry.getAbsolutePath());
	        } else {
	        		if(processedImageList.indexOf(fileEntry.getAbsolutePath())==-1)
	        		return extractBytes(fileEntry.getAbsolutePath());
	        }
	    }
		return null;
	}


}
