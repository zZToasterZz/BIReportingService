package com.reporting.bi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reporting.bi.entity.Customers;
import com.reporting.bi.entity.Orders;
import com.reporting.bi.entity.Products;
import com.reporting.bi.models.OrdersModel;
import com.reporting.bi.models.WhoDetailsModel;
import com.reporting.bi.repository.OrdersRepository;

import jakarta.annotation.Nonnull;

@Service
public class OrdersService {
	
	@Autowired
	CustomersService customersService;
	
	@Autowired
	ProductsService productsService;
	
	@Autowired
	OrdersRepository ordersRepository;
	
	public ResponseEntity<String> saveAllOrders(@Nonnull final List<OrdersModel> orders, 
												final WhoDetailsModel caller) {
		ordersRepository
			.saveAll(orders.stream().map(o -> getInsertable(o.customerid(), o.productid(), caller))
					.collect(Collectors.toList()));
		
		return new ResponseEntity<String>("saved", HttpStatus.CREATED);
	}
	
	public List<OrdersModel> getAllOrders() {
		List<Orders> data = ordersRepository.findAll();
		
		List<OrdersModel> orders = data.stream().map(o -> {
			return new OrdersModel(o.getOrderid(), o.getCustomers().getCustomerid(), o.getProducts().getProductid());
		}).collect(Collectors.toList());
		
		return orders;
	}
	
	private Orders getInsertable(final UUID customerid, final UUID productid, final WhoDetailsModel caller) {
		
		final Customers customers = customersService.getById(customerid);
		final Products products = productsService.getById(productid);
		
		final Orders orders = new Orders(null, customers, products);
		orders.setCreatedBy(caller.createdBy());
		orders.setModifiedBy(caller.createdBy());
		return orders;
	}
	
	private Orders getUpdatable(final UUID customerid, final UUID productid, final WhoDetailsModel caller) {
		
		final Customers customers = customersService.getById(customerid);
		final Products products = productsService.getById(productid);
		
		final Orders orders = new Orders(null, customers, products);
		orders.setModifiedBy(caller.modifiedBy());
		orders.setCreatedOn(null);
		orders.setLastUpdatedOn(LocalDateTime.now());
		return orders;
	}
}
