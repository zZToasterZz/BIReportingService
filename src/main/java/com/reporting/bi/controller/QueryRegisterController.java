package com.reporting.bi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.reporting.bi.models.QueryRegisterModel;
import com.reporting.bi.service.QueryRegisterService;
import com.reporting.bi.utility.CommonBeansGenerator;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/query")
public class QueryRegisterController {
	
	@Autowired
	QueryRegisterService queryRegisterService;
	
	@Autowired
	CommonBeansGenerator generator;
	
	@PostMapping("/register")
	public ResponseEntity<?> registerQuery(@RequestBody final QueryRegisterModel queryRegisterModel) {
		return queryRegisterService.saveQuery(queryRegisterModel, generator.getCaller());
	}
	
	@GetMapping(path = "/getsampledata/{id}", produces = {MediaType.APPLICATION_XML_VALUE}) 
	public void getSampleData(@PathVariable("id") String queryId, HttpServletResponse response) 
			throws SAXException, IOException, ParserConfigurationException, TransformerException {
		
		final String dataFilePath = queryRegisterService.generateDataFile(queryId);
		
		final File file = new File(dataFilePath);
		if(!file.exists()){
	    	System.out.println("ERROR : File not found");
		}
		
		response.setHeader("content-type", MediaType.APPLICATION_XML_VALUE);
	    response.setHeader("content-length", String.valueOf(file.length()));
	    response.setHeader("content-disposition", "inline; filename=\"" + file.getName() + "\"");
	    
	    Files.copy(file.toPath(), response.getOutputStream());
	}
}
