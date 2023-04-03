package com.reporting.bi.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.reporting.bi.properties.FilePathProperties;

@Component
public class FileHandlingUtil {
	
	@Autowired
	FilePathProperties filePathProperties;
	
	@Autowired
	ParamsUtil paramsUtil;
	
	private final static String xmlHeader = "<?xml version = '1.0' encoding = 'UTF-8'?>";
	
	public void deleteFileIfExists(String path) throws IOException {
		path = FilenameUtils.separatorsToWindows(path);
		Files.deleteIfExists(Paths.get(path));
	}
	
	public String createDataFileXML(String dataFileName, String xmlData) throws IOException {
		
		xmlData = xmlHeader+xmlData;
		
		dataFileName = paramsUtil.sanitizeFileName(dataFileName);
		
		String dataFilePath = new File("").getAbsolutePath() 
				+ File.separator + filePathProperties.getDataLocation() 
				+ File.separator + dataFileName + ".xml";
		
		dataFilePath = FilenameUtils.separatorsToSystem(dataFilePath);
		
		final FileWriter fw = new FileWriter(dataFilePath);
	    fw.write(xmlData);
	    fw.close();
	    
	    return dataFilePath;
	}
	
	public String uploadTemplateFile(final MultipartFile template, final String reportName) throws IOException {
		
		String reportTemplatePath = new File("").getAbsolutePath() 
				+ File.separator + filePathProperties.getTemplatesLocation() 
				+ File.separator + reportName + ".rtf";
		
		reportTemplatePath = FilenameUtils.separatorsToSystem(reportTemplatePath);
		
		deleteFileIfExists(reportTemplatePath);
		
		final File target = new File(reportTemplatePath);
		int readByteCount = 0;
		final byte[] buffer = new byte[4096];

		final BufferedInputStream in= new BufferedInputStream(template.getInputStream());
		final FileOutputStream out = new FileOutputStream(target);
	    while ((readByteCount = in.read(buffer)) != -1) {
	        out.write(buffer, 0, readByteCount);
	    }
	    out.flush();
		out.close();
		
		return reportTemplatePath;
	}
	
	public void copyFile(final String source, final String target) throws IOException {
		final File s = new File(source);
		final File t = new File(target);
		FileUtils.copyDirectory(s, t);
	}
}