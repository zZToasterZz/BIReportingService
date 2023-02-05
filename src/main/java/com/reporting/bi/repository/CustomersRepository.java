package com.reporting.bi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reporting.bi.entity.Customers;

public interface CustomersRepository extends JpaRepository<Customers, UUID>{

}