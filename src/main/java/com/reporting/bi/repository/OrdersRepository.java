package com.reporting.bi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reporting.bi.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {

}