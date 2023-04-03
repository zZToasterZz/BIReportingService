package com.reporting.bi.entity;

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
@Table(name = "report_register", 
	uniqueConstraints = {
			@UniqueConstraint(name = "UniqueByQueryAndTemplate", columnNames = {"queryid", "templateid"})
	})
public class ReportRegister extends WhoDetailsColumns {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@JdbcTypeCode(java.sql.Types.VARCHAR)
	private UUID reportid;
	
	@Column
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "queryid", referencedColumnName = "queryid", insertable = true, updatable = false)
	private QueryRegister queryRegister;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "templateid", referencedColumnName = "templateid", insertable = true, updatable = false)
	private TemplateRegister templateRegister;
}