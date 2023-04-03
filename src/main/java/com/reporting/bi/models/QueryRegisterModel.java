package com.reporting.bi.models;

import java.util.UUID;

import com.reporting.bi.entity.QueryRegister;

import jakarta.annotation.Nullable;

public record QueryRegisterModel(@Nullable UUID queryId, String name, String query) {
	
	public QueryRegister getInsertable(final String caller) {
		final QueryRegister queryRegister = new QueryRegister(null, this.name, this.query);
		queryRegister.setCreatedBy(caller);
		queryRegister.setModifiedBy(caller);
		
		return queryRegister;
	}
}
