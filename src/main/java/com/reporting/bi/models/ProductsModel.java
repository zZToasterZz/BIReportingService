package com.reporting.bi.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.reporting.bi.constants.Currency;
import com.reporting.bi.entity.Products;

public record ProductsModel(UUID productid, 
		String name, 
		String description, 
		Double price, 
		Currency currency) {
	
	public Products getInsertable(final String caller) {
		final Products products = new Products(null, name, description, price, currency);
		products.setCreatedBy(caller);
		products.setModifiedBy(caller);
		return products;
	}
	
	public Products getUpdatable(final WhoDetailsModel caller) {
		final Products products = new Products(null, name, description, price, currency);
		products.setModifiedBy(caller.modifiedBy());
		products.setCreatedOn(null);
		products.setLastUpdatedOn(LocalDateTime.now());
		return products;
	}
}
