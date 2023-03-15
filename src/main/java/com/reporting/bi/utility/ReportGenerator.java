package com.reporting.bi.utility;

import static com.reporting.bi.constants.ReportFileType.PDF;
import static com.reporting.bi.constants.ReportFileType.RTF;
import static com.reporting.bi.constants.ReportFileType.XSL;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.reporting.bi.constants.ReportFileType;
import com.reporting.bi.exception.UnsupportedException;
import com.reporting.bi.properties.FilePathProperties;

import oracle.xdo.XDOException;
import oracle.xdo.template.FOProcessor;
import oracle.xdo.template.RTFProcessor;

@Component
public class ReportGenerator {

	@Autowired
	FilePathProperties filePathProperties;

	@Autowired
	ParamsUtil paramsUtil;
	
	@Autowired
	FileHandlingUtil fileHandlingUtil;

	private String RTFtoPDF(final String inputFileName, 
						 final String dataFileXMLname) throws XDOException, IOException {
		
		final String workingDirectory = new File("").getAbsolutePath() 
				+ File.separator + filePathProperties.getProcessingLocation();
		
		final String inputFileRTFpath =  new File("").getAbsolutePath() 
				+ File.separator + filePathProperties.getTemplatesLocation()
				+File.separator + inputFileName + "." + RTF.extension();
		final String dataFileXMLpath = new File("").getAbsolutePath() 
				+ File.separator + filePathProperties.getDataLocation()
				+File.separator + dataFileXMLname + ".xml";

		final String outputFileXSLpath = workingDirectory + File.separator + inputFileName + "." + XSL.extension();
		final String outputFilePDFpath = workingDirectory + File.separator + inputFileName + "." + PDF.extension();
		
		RTFtoXSL(inputFileRTFpath, outputFileXSLpath); //Create XSL from RTF in working directory
		XSLtoPDF(outputFileXSLpath, dataFileXMLpath, outputFilePDFpath); //Combine XSL and XML to form PDF in working directory
		
		//Clean up unnecessary files
		fileHandlingUtil.deleteFileIfExists(outputFileXSLpath);
		fileHandlingUtil.deleteFileIfExists(dataFileXMLname);
		
		return outputFilePDFpath;
	}
	
	private void RTFtoXSL(final String inputFileRTF, final String outputFileXSL) throws IOException, XDOException {
		final RTFProcessor rtfProcessor = new RTFProcessor(inputFileRTF);
		rtfProcessor.setOutput(outputFileXSL); // Convert RTF to XSL style sheet
		rtfProcessor.process();
	}
	
	private void XSLtoPDF(final String inputFileXSL, final String dataFileXML, final String outputFilePDF) throws XDOException {
		final FOProcessor foProcessor = new FOProcessor();
		foProcessor.setData(dataFileXML);
		foProcessor.setTemplate(inputFileXSL);
		foProcessor.setOutput(outputFilePDF);
		foProcessor.generate(); // Merge XSL style sheet with XML Data and generate PDF
	}

	public String process(final ReportFileType input, 
						final ReportFileType output, 
						String inputFileName,
						String dataFileXMLname) throws Exception {

		inputFileName = paramsUtil.sanitizeFileName(inputFileName);

		if (RTF == input && PDF == output) {
			return RTFtoPDF(inputFileName, dataFileXMLname);
		} else {
			throw new UnsupportedException("Unsupported conversion from " + input.name() + " to " + output.name());
		}
	}
}