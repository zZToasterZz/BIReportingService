package com.reporting.bi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reporting.bi.models.OrdersModel;
import com.reporting.bi.service.OrdersService;
import com.reporting.bi.utility.CommonBeansGenerator;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
	
	@Autowired
	OrdersService ordersService;
	
	@Autowired
	CommonBeansGenerator generator;
	
	@PostMapping
	public ResponseEntity<?> saveAllOrders(
			@RequestBody(required = true) final List<OrdersModel> orders) {
		
		return ordersService.saveAllOrders(orders, generator.getCaller());
	}
}