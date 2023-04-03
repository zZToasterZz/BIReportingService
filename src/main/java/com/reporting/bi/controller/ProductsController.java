package com.reporting.bi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.bi.models.ProductsModel;
import com.reporting.bi.service.ProductsService;
import com.reporting.bi.utility.CommonBeansGenerator;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
	
	@Autowired
	ProductsService productsService;
	
	@Autowired
	CommonBeansGenerator generator;
	
	@PostMapping
	public ResponseEntity<?> saveAllProducts(
			@RequestBody(required = true) final List<ProductsModel> products) {
		
		return productsService.saveAll(products, generator.getCaller());
	}
}