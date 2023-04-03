package com.reporting.bi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class WhoDetailsColumns {
	
	@Column(length = 50)
	@NotNull
	private String createdBy;
	
	@Column(length = 50)
	@NotNull
	private String modifiedBy;
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private LocalDateTime lastUpdatedOn = LocalDateTime.now();
	
	@Column
	@NotNull
	private Boolean isActive = true;
}