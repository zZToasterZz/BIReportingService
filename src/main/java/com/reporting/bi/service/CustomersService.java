package com.reporting.bi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reporting.bi.models.CustomersRecord;
import com.reporting.bi.models.WhoDetailsRecord;
import com.reporting.bi.repository.CustomersRepository;

@Service
public class CustomersService {

	@Autowired
	CustomersRepository customersRepository;
	
	public ResponseEntity<String> saveAll(final List<CustomersRecord> customersRecord, WhoDetailsRecord caller) {
		
		customersRepository
			.saveAll(customersRecord.stream().map(c -> c.getInsertable(caller.createdBy()))
					.collect(Collectors.toList()));
		
		return new ResponseEntity<String>("saved", HttpStatus.CREATED);
	}
}
