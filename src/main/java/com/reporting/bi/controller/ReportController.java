package com.reporting.bi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.reporting.bi.service.ReportsService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/report")
public class ReportController {
	
	@Autowired
	ReportsService reportService;

	@GetMapping(path = "/generatedatafile", produces = {MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<?> getBillReport(@RequestParam("data-file-name") String dataFileName) 
			throws SAXException, IOException, ParserConfigurationException, TransformerException {
		
		final String xml = reportService.generateDataXml(dataFileName);
		
		return new ResponseEntity<String>(xml, HttpStatus.OK);
	}
	
	@PostMapping(path = "/uploadtemplate", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> uploadTemplate(@RequestParam("template") MultipartFile tempalte, 
											@RequestParam("report-name") final String reportName) throws IOException {
		
		final String message = reportService.uploadTemplate(tempalte, reportName);
		
		return new ResponseEntity<String>(message, HttpStatus.OK);
	}
	
	@GetMapping(path = "/generatereport", produces = {MediaType.APPLICATION_PDF_VALUE})
	public void generatePdfReport(@RequestParam("data-file-name") String dataFileName,
								  @RequestParam("report-file-name") String reportFileName, 
								  HttpServletResponse response) throws Exception {
		
		final String filepath = reportService.generateReport(dataFileName, reportFileName);
		
		final File file = new File(filepath);
		if(!file.exists()){
	    	System.out.println("ERROR : File not found");
		}
		
		response.setHeader("content-type", MediaType.APPLICATION_PDF_VALUE);
	    response.setHeader("content-length", String.valueOf(file.length()));
	    response.setHeader("content-disposition", "inline; filename=\"" + file.getName() + "\"");
	    
	    Files.copy(file.toPath(), response.getOutputStream());
	}
}