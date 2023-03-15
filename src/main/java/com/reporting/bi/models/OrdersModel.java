package com.reporting.bi.models;

import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;


public record OrdersModel(@Nullable UUID orderid, @NotNull UUID customerid, @NotNull UUID productid) {
	
	public OrdersModel(@NotNull final UUID customerid, @NotNull final UUID productid) {
		this(null, customerid, productid);
	}
}