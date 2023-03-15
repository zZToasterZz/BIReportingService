package com.reporting.bi.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "customers", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "phone"}))
public class Customers extends WhoDetailsColumns implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID customerid;
	
	@Column(length = 100)
	private String name;
	
	@Column(length = 100, unique = true)
	private String email;
	
	@Column(length = 15)
	private String phone;
	
	@Column(length = 200)
	private String address;
	
	@Column(length = 1)
	private String gender;
	
	@OneToMany(mappedBy = "customers", fetch = FetchType.LAZY)
	private List<Orders> orders;

	public Customers(UUID customerid, String name, String email, String phone, String address, String gender) {
		super();
		this.customerid = customerid;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.gender = gender;
	}
}