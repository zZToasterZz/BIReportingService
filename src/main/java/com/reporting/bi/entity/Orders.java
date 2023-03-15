package com.reporting.bi.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders", 
	uniqueConstraints = {
			@UniqueConstraint(name = "UniqueByCustomerAndProduct", columnNames = {"customerid", "productid"})
	})
public class Orders extends WhoDetailsColumns implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID orderid;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerid", referencedColumnName = "customerid", insertable = true, updatable = false)
	private Customers customers;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid", referencedColumnName = "productid", insertable = true, updatable = false)
	private Products products;
}