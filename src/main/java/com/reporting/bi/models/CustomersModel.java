package com.reporting.bi.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.reporting.bi.entity.Customers;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record CustomersModel(@Nullable UUID customerid, 
		String name, 
		String email, 
		String phone, 
		String address, 
		String gender) {
	
	public Customers getInsertable(@NotNull String caller) {
		final Customers customers = new Customers(null, this.name, this.email, this.phone, this.address, this.gender);
		customers.setCreatedBy(caller);
		customers.setModifiedBy(caller);
		return customers;
	}
	
	public Customers getUpdatable(@NotNull WhoDetailsModel caller) {
		final Customers customers = new Customers(this.customerid, this.name, this.email, this.phone, this.address, this.gender);
		customers.setModifiedBy(caller.modifiedBy());
		customers.setCreatedOn(null);
		customers.setLastUpdatedOn(LocalDateTime.now());
		return customers;
	}
