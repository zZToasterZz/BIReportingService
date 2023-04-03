package com.reporting.bi.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import com.reporting.bi.constants.Currency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products")
public class Products extends WhoDetailsColumns implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID productid;

	@Column(length = 100)
	private String name;

	@Column(length = 500)
	private String description;

	@Column(length = 15)
	private Double price;

	@Column
	@Enumerated(value = EnumType.STRING)
	private Currency currency;
	
	@OneToMany(mappedBy = "products", fetch = FetchType.LAZY)
	private List<Orders> orders;

	public Products(UUID productid, String name, String description, Double price, Currency currency) {
		this.productid = productid;
		this.name = name;
		this.description = description;
		this.price = price;
		this.currency = currency;
	}
}