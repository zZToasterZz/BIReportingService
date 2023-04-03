package com.reporting.bi.service;

import static com.reporting.bi.constants.ReportFileType.PDF;
import static com.reporting.bi.constants.ReportFileType.RTF;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reporting.bi.entity.QueryRegister;
import com.reporting.bi.entity.ReportRegister;
import com.reporting.bi.entity.TemplateRegister;
import com.reporting.bi.models.ReportRegisterModel;
import com.reporting.bi.models.WhoDetailsModel;
import com.reporting.bi.repository.QueryRegisterRepository;
import com.reporting.bi.repository.ReportRegisterRepository;
import com.reporting.bi.repository.TemplateRegisterRepository;
import com.reporting.bi.utility.ReportGenerator;

@Service
public class ReportRegisterService {
	
	@Autowired
	QueryRegisterRepository queryRegisterRepository;
	
	@Autowired
	QueryRegisterService queryRegisterService;
	
	@Autowired
	TemplateRegisterRepository templateRegisterRepository;
	
	@Autowired
	ReportRegisterRepository reportRegisterRepository;
	
	@Autowired
	ReportGenerator reportGenerator;
	
	public ResponseEntity<String> registerReport(final ReportRegisterModel reportRegisterModel,
												final WhoDetailsModel caller) {
		final ReportRegister reportRegister = getInsertable(reportRegisterModel.queryId(), 
				reportRegisterModel.templateId(), reportRegisterModel.name(), caller);
		
		reportRegisterRepository.save(reportRegister);
		
		return new ResponseEntity<String>("report registered", HttpStatus.CREATED);
	}
	
	public String generateReport(final String reportId) throws Exception {
		
		final ReportRegister reportRegister = reportRegisterRepository.getReferenceById(UUID.fromString(reportId));
		final String templateName = reportRegister.getTemplateRegister().getName();
		final String queryId = reportRegister.getQueryRegister().getQueryid().toString();
		queryRegisterService.generateDataFile(queryId);
		final String reportPath = reportGenerator.process(RTF, PDF, templateName, queryId);
		
		return reportPath;
	}
	
	private ReportRegister getInsertable(final UUID queryId,
										final UUID templateId,
										final String name,
										final WhoDetailsModel caller) {
		final QueryRegister queryRegister = queryRegisterRepository.getReferenceById(queryId);
		final TemplateRegister templateRegister = templateRegisterRepository.getReferenceById(templateId);
		
		final ReportRegister reportRegister = new ReportRegister(null, name, queryRegister, templateRegister);
		reportRegister.setCreatedBy(caller.createdBy());
		reportRegister.setModifiedBy(caller.modifiedBy());
		return reportRegister;
	}
}