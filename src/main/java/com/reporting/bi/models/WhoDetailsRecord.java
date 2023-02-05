package com.reporting.bi.models;

import java.time.LocalDateTime;

public record WhoDetailsRecord(String createdBy, 
		String modifiedBy, 
		LocalDateTime createdOn, 
		LocalDateTime lastUpdatedOn, 
		boolean isActive) {

}
