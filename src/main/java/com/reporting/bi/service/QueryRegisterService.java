package com.reporting.bi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.reporting.bi.entity.QueryRegister;
import com.reporting.bi.models.QueryRegisterModel;
import com.reporting.bi.models.WhoDetailsModel;
import com.reporting.bi.repository.QueryRegisterRepository;
import com.reporting.bi.utility.FileHandlingUtil;
import com.reporting.bi.utility.ReportGenerator;

import jakarta.persistence.EntityManager;

@Service
public class QueryRegisterService {
	
	@Autowired
	QueryRegisterRepository queryRegisterRepository;
	
	@Autowired
	EntityManager em;
	
	@Autowired
	Session hibernateSession;

	@Autowired
	FileHandlingUtil fileHandlingUtil;
	
	@Autowired
	ReportGenerator reportGenerator;
	
	public ResponseEntity<?> saveQuery(final QueryRegisterModel queryRegisterModel, final WhoDetailsModel caller) {
		final QueryRegister queryRegister = queryRegisterModel.getInsertable(caller.createdBy());
		
		queryRegisterRepository.save(queryRegister);
		
		return new ResponseEntity<String>("saved", HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String generateDataFile(final String queryId) throws SAXException, IOException, ParserConfigurationException, TransformerException {
		final QueryRegister queryRegister = queryRegisterRepository.getReferenceById(UUID.fromString(queryId));
		final String SQL = queryRegister.getQuery();
		final Query<Map<String, Object>> query = hibernateSession.createNativeQuery(SQL);
		query.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		final String xml = getXmlStringFromListMap(query.list());
		
		final String dataFilePath = fileHandlingUtil.createDataFileXML(queryRegister.getQueryid().toString(), xml);
		
		return dataFilePath;
	}
	
	private String getXmlStringFromListMap(List<Map<String,Object>> data) throws JsonProcessingException {
		return new XmlMapper().writeValueAsString(data);
	}
}