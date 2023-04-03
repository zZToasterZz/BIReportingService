package com.reporting.bi.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class FilePathProperties {
	
	@Value("${reports.base.location}")
	private String reportsBaseLocation;
	
	@Value("${reports.processing.location}")
	private String processingLocation;
	
	@Value("${reports.storage.templates.location}")
	private String templatesLocation;
	
	@Value("${reports.storage.data.location}")
	private String dataLocation;
}