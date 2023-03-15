package com.reporting.bi.constants;

public enum ReportFileType {
	XSL("xsl"),
	PDF("pdf"),
	RTF("rtf"),
	XLS("xls"),
	HTML("html"),
	EDI("edi"),
	EFT("eft");
	
	private final String extension;
	
	private ReportFileType(final String extension) {
		this.extension = extension;
	}
	
	public String extension() {
		return this.extension;
	}
}