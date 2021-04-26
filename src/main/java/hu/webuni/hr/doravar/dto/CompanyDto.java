package hu.webuni.hr.doravar.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import hu.webuni.hr.doravar.model.CompanyType;

public class CompanyDto {

	@JsonView(Views.BaseData.class)
	private long id;
	@JsonView(Views.BaseData.class)
	private long registrationNumber;
	@JsonView(Views.BaseData.class)
	private String name;
	@JsonView(Views.BaseData.class)
	private String address;
//	private CompanyType companyType;
	private String companyType;
	private List<EmployeeDto> employees = new ArrayList<>();
	
	
	public long getId() {
		return id;
	}

	public CompanyDto() {
	}

	public CompanyDto(long id, long registrationNumber, String name, String address, List<EmployeeDto> employees) {
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
		this.employees = employees;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(long registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

//	public CompanyType getCompanyType() {
//		return companyType;
//	}
//
//	public void setCompanyType(CompanyType companyType) {
//		this.companyType = companyType;
//	}

	
}
