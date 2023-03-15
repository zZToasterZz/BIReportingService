package com.reporting.bi.reportdtos;

import java.sql.Timestamp;
import java.util.UUID;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@JacksonXmlRootElement(localName = "bill")
public class BillingReport {
	private UUID orderid;
	private UUID customerid;
	private String name;
	private String email;
	private String gender;
	private String phone;
	private String address;
	private UUID productid;
	private String product_name;
	private String description;
	private Double price;
	private Double running_total;
	private Double customer_sum;
	private Timestamp created_on;
	private String createdBy;
	
	@Override
	public String toString() {
		return "BillingReport [orderid=" + orderid + ", customerid=" + customerid + ", name=" + name + ", email="
				+ email + ", gender=" + gender + ", phone=" + phone + ", address=" + address + ", productid="
				+ productid + ", product_name=" + product_name + ", description=" + description + ", price=" + price
				+ ", running_total=" + running_total + ", customer_sum=" + customer_sum + ", created_on=" + created_on
				+ ", createdBy=" + createdBy + "]";
	}
}