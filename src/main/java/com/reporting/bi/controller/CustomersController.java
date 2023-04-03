package com.reporting.bi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.bi.models.CustomersModel;
import com.reporting.bi.service.CustomersService;
import com.reporting.bi.utility.CommonBeansGenerator;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
	
	@Autowired
	CustomersService customersService;
	
	@Autowired
	CommonBeansGenerator generator;
	
	@PostMapping
	public ResponseEntity<?> saveAllCustomers(
			@RequestBody(required = true) final List<CustomersModel> customers) {
		
		return customersService.saveAll(customers, generator.getCaller());
	}
}