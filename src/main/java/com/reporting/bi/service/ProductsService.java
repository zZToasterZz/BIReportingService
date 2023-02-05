package com.reporting.bi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reporting.bi.models.ProductsRecord;
import com.reporting.bi.models.WhoDetailsRecord;
import com.reporting.bi.repository.ProductsRepository;

@Service
public class ProductsService {
	
	@Autowired
	ProductsRepository productsRepository;
	public ResponseEntity<String> saveAll(final List<ProductsRecord> productsRecords, WhoDetailsRecord caller) {
		productsRepository.saveAll(productsRecords.stream().map(i -> i.getInsertable(caller.createdBy()))
				.collect(Collectors.toList()));
		
		return new ResponseEntity<String>("saved", HttpStatus.CREATED);
	}
}