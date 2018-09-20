package com.dimensionless.ImageStream.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dimensionless.ImageStream.model.Response;

@Controller
public class FrontController {

	private static final String inputPath = "/Users/santanu/Downloads/katy";
	private static final String outputPath = "/Users/santanu/Downloads/future/";
	
	static Set<String> processedImageList = new HashSet();
	static boolean isAllOldFileMoved = false;
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	@ResponseBody
	public Response sendImageByte() throws Exception {
		
		if(isAllOldFileMoved)
			return null;
		
		Response response = new Response();
		if(processedImageList.size()==0) {
			response.setIsFirstImage("1");
		}
		
		
		response.setBytes(scanFoler(inputPath, response));
		System.out.println(processedImageList);
		if(processedImageList.size()==5) {
			moveOldFiles("");
			processedImageList.clear();
		}
		return response;
	}

	@MessageMapping("/clean")
	@ResponseBody
	public Response clean() throws Exception {
		processedImageList = new HashSet();
		return null;
	}

	public byte[] extractBytes(String fileName) throws IOException {

		File fi = new File(inputPath + "/" + fileName);
		byte[] fileContent = Files.readAllBytes(fi.toPath());
		// System.out.println(fi.getName());
		return fileContent;
	}

	public byte[] scanFoler(final String folder, Response response) throws Exception {
		for (final File fileEntry : new File(folder).listFiles()) {

			// extract the content if not alert file
			// processedImageList.indexOf(fileEntry.getAbsolutePath())==-1 &&
			if (!processedImageList.contains(fileEntry.getName()) && !fileEntry.getAbsolutePath().endsWith(".alart")
					&& fileEntry.getName().endsWith(".jpg")) {
				response.setContent(fileEntry.getName());
				processedImageList.add(fileEntry.getName());
				if (processedImageList.size() == 5) {

					//moveOldFiles("");
					/*String oldFileName = processedImageList.iterator().next();

					String newFileNameInitial = fileEntry.getName().substring(0, fileEntry.getName().lastIndexOf("_"));
					String oldFileNameInitial = oldFileName.substring(0, oldFileName.lastIndexOf("_"));
					if (!oldFileNameInitial.equals(newFileNameInitial)) {
						try {
							processedImageList.clear();
							processedImageList.add(fileEntry.getName());
							moveOldFiles(fileEntry.getName());
						} catch (Exception e) {
						}
					}*/
				}
				return extractBytes(fileEntry.getName());
			}

		}
		return null;
	}

	public static void moveOldFiles(String fileName) throws Exception {
		isAllOldFileMoved = true;
		for (final String ipFileName : processedImageList) {
			if (!ipFileName.equals(fileName)) {
				File srcFile = new File(inputPath + "/" + ipFileName);
				srcFile.renameTo(new File(outputPath + ipFileName));
			}
		}
		isAllOldFileMoved =false;
	}

}
