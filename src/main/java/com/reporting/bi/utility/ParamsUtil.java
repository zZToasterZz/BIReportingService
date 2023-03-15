package com.reporting.bi.utility;

import org.springframework.stereotype.Component;

@Component
public class ParamsUtil {
	
	String sanitizeFileName(String filename) {
		return filename.replaceAll("[^a-zA-Z0-9]", "");
	}
}