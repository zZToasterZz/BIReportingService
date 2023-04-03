package com.reporting.bi.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reporting.bi.entity.TemplateRegister;
import com.reporting.bi.models.TemplateRegisterModel;
import com.reporting.bi.models.WhoDetailsModel;
import com.reporting.bi.repository.TemplateRegisterRepository;
import com.reporting.bi.utility.FileHandlingUtil;

@Service
public class TemplateRegisterService {

	@Autowired
	TemplateRegisterRepository templateRegisterRepository;
	
	@Autowired
	FileHandlingUtil fileHandlingUtil;

	public ResponseEntity<String> saveTemplate(final String reportName,
											   final MultipartFile template,
											   final WhoDetailsModel caller) throws IOException {
		final String extension = "rtf";
		final String name = UUID.randomUUID().toString();
		final String path = uploadTemplate(template, name);
		
		final TemplateRegister templateRegister = getInsertable(name, reportName, path, 
				extension, caller.createdBy());

		templateRegisterRepository.save(templateRegister);

		return new ResponseEntity<String>("template saved", HttpStatus.CREATED);
	}
	
	private String uploadTemplate(final MultipartFile template, final String reportName) throws IOException {
		
		return fileHandlingUtil.uploadTemplateFile(template, reportName);
	}
	
	public TemplateRegister getInsertable(final String name, 
										  final String originalFileName, 
										  final String path, 
										  final String extension, 
										  final String caller) {
		final TemplateRegister templateRegister = new TemplateRegister(null, name, originalFileName, path, extension);
		templateRegister.setCreatedBy(caller);
		templateRegister.setModifiedBy(caller);
		
		return templateRegister;
	}
}