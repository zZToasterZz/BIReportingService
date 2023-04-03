package com.reporting.bi.service;

import static com.reporting.bi.constants.ReportFileType.PDF;
import static com.reporting.bi.constants.ReportFileType.RTF;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.reporting.bi.utility.FileHandlingUtil;
import com.reporting.bi.utility.ReportGenerator;

import jakarta.persistence.EntityManager;

@Service
public class ReportsService {
	
	@Autowired
	EntityManager em;
	
	@Autowired
	Session hibernateSession;

	@Autowired
	FileHandlingUtil fileHandlingUtil;
	
	@Autowired
	ReportGenerator reportGenerator;
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String generateDataXml(final String dataFileName) 
			throws SAXException, IOException, ParserConfigurationException, TransformerException {

		final String SQL = """
				SELECT
					a.orderid, b.customerid, b.name, b.email, b.gender, b.phone, b.address, c.productid, c.name as product_name, c.description, c.price,
				    sum(c.price) over(partition by b.customerid order by a.created_on rows between unbounded preceding and current row) as running_total,
				    sum(c.price) over(partition by b.customerid) as customer_sum,
				    a.created_on, a.created_by
				FROM orders a
				INNER JOIN customers b
					ON a.customerid = b.customerid
				INNER JOIN products c
					ON a.productid = c.productid
				ORDER BY
					a.created_on
				""";

		final Query<Map<String, Object>> query = hibernateSession.createNativeQuery(SQL);
		query.setTupleTransformer(AliasToEntityMapResultTransformer.INSTANCE);

		final String xml = getXmlStringFromListMap(query.list());
		
		fileHandlingUtil.createDataFileXML(dataFileName, xml);
		
		return xml;
	}
	
	private String getXmlStringFromListMap(List<Map<String,Object>> data) throws JsonProcessingException {
		return new XmlMapper().writeValueAsString(data);
	}
	
	public String uploadTemplate(final MultipartFile template, final String reportName) throws IOException {
		
		fileHandlingUtil.uploadTemplateFile(template, reportName);
		
		return "uploaded template: "+reportName;
	}
	
	public String generateReport(final String dataFileName, final String reportFileName) throws Exception {
		
		final String reportPath = reportGenerator.process(RTF, PDF, reportFileName, dataFileName);
		
		return reportPath;
	}
}