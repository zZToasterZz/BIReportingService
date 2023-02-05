package com.reporting.bi.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.bi.models.CustomersRecord;
import com.reporting.bi.models.ProductsRecord;
import com.reporting.bi.models.WhoDetailsRecord;
import com.reporting.bi.service.CustomersService;
import com.reporting.bi.service.ProductsService;

@RestController
@RequestMapping("/api")
public class MainController {
	
	@Autowired
	CustomersService customersService;
	
	@Autowired
	ProductsService productsService;
	
	@PostMapping(path = "/customers")
	public ResponseEntity<?> saveAllCustomers(@RequestBody(required = true) final List<CustomersRecord> customers) {
		return customersService.saveAll(customers,
				new WhoDetailsRecord("shantanu", "shantanu",
						LocalDateTime.now(), LocalDateTime.now(), true));
	}
	
	@PostMapping(path = "/products")
	public ResponseEntity<?> saveAllProducts(@RequestBody(required = true) final List<ProductsRecord> products) {
		return productsService.saveAll(products,
				new WhoDetailsRecord("shantanu", "shantanu",
						LocalDateTime.now(), LocalDateTime.now(), true));
	}
}