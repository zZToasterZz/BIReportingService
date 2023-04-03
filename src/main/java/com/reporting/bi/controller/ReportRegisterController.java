package com.reporting.bi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.bi.models.ReportRegisterModel;
import com.reporting.bi.service.ReportRegisterService;
import com.reporting.bi.utility.CommonBeansGenerator;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/report")
public class ReportRegisterController {

	@Autowired
	ReportRegisterService reportRegisterService;
	
	@Autowired
	CommonBeansGenerator generator;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerReport(@RequestBody final ReportRegisterModel reportRegisterModel) {
		return reportRegisterService.registerReport(reportRegisterModel, generator.getCaller());
	}
	
	@GetMapping(path = "/generate/{id}", produces = {MediaType.APPLICATION_PDF_VALUE})
	public void generateReport(@PathVariable("id") final String reportId, HttpServletResponse response) throws Exception {
		
		final String reportPdfPath = reportRegisterService.generateReport(reportId);
		
		final File file = new File(reportPdfPath);
		if(!file.exists()){
	    	System.out.println("ERROR : File not found");
		}
		
		response.setHeader("content-type", MediaType.APPLICATION_PDF_VALUE);
	    response.setHeader("content-length", String.valueOf(file.length()));
	    response.setHeader("content-disposition", "inline; filename=\"" + file.getName() + "\"");
	    
	    Files.copy(file.toPath(), response.getOutputStream());
	}
}
