package hu.webuni.hr.doravar.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue
	private long id;
	private long registrationNumber;
	private String name;
	private String address;
//	private CompanyType companyType;
	private String companyType;

	@OneToMany(mappedBy = "company")
//	@OneToMany(mappedBy = "company", cascade = {CascadeType.MERGE, CascadeType.PERSIST}) // ld service-ben addEmployee metódus
	private List<Employee> employees = new ArrayList<>();

	public long getId() {
		return id;
	}

	public Company() {
	}

	public Company(long id, long registrationNumber, String name, String address, List<Employee> employees) {
		this.id = id;
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
		this.employees = employees;
	}

	public Company(long registrationNumber, String name, String address) {
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.address = address;
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

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

//	public CompanyType getCompanyType() {
//		return companyType;
//	}
//
//	public void setCompanyType(CompanyType companyType) {
//		this.companyType = companyType;
//	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public void addEmployee(Employee employee) { // itt a két irányú kapcsolatnak megfelelően mindkét entitásban
													// beállítjuk
		if (this.employees == null)
			this.employees = new ArrayList<>();
		this.employees.add(employee);
		employee.setCompany(this);
	}
}
