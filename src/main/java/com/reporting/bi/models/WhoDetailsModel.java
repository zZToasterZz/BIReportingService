package com.reporting.bi.models;

import java.time.LocalDateTime;

public record WhoDetailsModel(String createdBy, 
		String modifiedBy, 
		LocalDateTime createdOn, 
		LocalDateTime lastUpdatedOn, 
		boolean isActive) {

}