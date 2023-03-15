package com.reporting.bi.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reporting.bi.entity.Customers;
import com.reporting.bi.models.CustomersModel;
import com.reporting.bi.models.WhoDetailsModel;
import com.reporting.bi.repository.CustomersRepository;

@Service
public class CustomersService {

	@Autowired
	CustomersRepository customersRepository;
	
	public ResponseEntity<String> saveAll(final List<CustomersModel> customersRecord, WhoDetailsModel caller) {
		
		customersRepository
			.saveAll(customersRecord.stream().map(c -> c.getInsertable(caller.createdBy()))
					.collect(Collectors.toList()));
		
		return new ResponseEntity<String>("saved", HttpStatus.CREATED);
	}
	
	public Customers getById(final UUID customerid) {
		return customersRepository.getReferenceById(customerid);
	}
}
