package com.reporting.bi.entity;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
@Table(name = "template_register")
public class TemplateRegister extends WhoDetailsColumns {
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID templateid;
	
	@Column
	private String name;
	
	@Column
	private String originalFileName;
	
	@Column
	private String path;
	
	@Column(length = 6)
	private String extension;
}
