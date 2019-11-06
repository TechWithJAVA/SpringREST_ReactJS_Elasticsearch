package com.yash.es.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "empId", "empName", "companyName", "address" })
@JsonInclude(Include.NON_EMPTY)
public class Employee  {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 8555881903808617766L;

	@JsonProperty("EmpId")
	private String empId;

	@JsonProperty("EmpName")
	private String empName;

	@JsonProperty("CompanyName")
	private String companyName;

	@JsonProperty("Address")
	private Address address;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", companyName=" + companyName + ", address="
				+ address + "]";
	}

}
