package com.reporting.bi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.reporting.bi.service.TemplateRegisterService;
import com.reporting.bi.utility.CommonBeansGenerator;

@RestController
@RequestMapping("/api/template")
public class TemplateRegisterController {
	
	@Autowired
	TemplateRegisterService templateRegisterService;
	
	@Autowired
	CommonBeansGenerator generator;
	
	@PostMapping(path = "/register", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<?> registerTemplate(@RequestParam("template") MultipartFile template, 
			@RequestParam("report-name") final String reportName) throws IOException {
		
		return templateRegisterService.saveTemplate(reportName, template, generator.getCaller());
	}
}