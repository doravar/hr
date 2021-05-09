package hu.webuni.hr.doravar.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Position;

public class EmployeeDto {

	private Long id;
	@NotEmpty(message = "name has to be present")
	private String name;
	@Min(value = 0, message = "salary has to be positive") // @Positive is lehet
	private int salary;
	@Past(message = "entryDate has to be past")
	private LocalDate entryDate;

	private String positionName;
	
	private String companyName;
	
	
	public EmployeeDto() {
	}

	public EmployeeDto(String name, int salary, LocalDate entryDate, String positionName, String companyName) {
		this.name = name;
		this.salary = salary;
		this.entryDate = entryDate;
		this.companyName = companyName;
		this.positionName = positionName;
	}

	public EmployeeDto(Long id, String name, int salary, LocalDate entryDate, String positionName, String companyName) {
		this.id =id;
		this.name = name;
		this.salary = salary;
		this.entryDate = entryDate;
		this.companyName = companyName;
		this.positionName = positionName;
	}

	public EmployeeDto(String name, int salary, LocalDate entryDate) {
		this.name = name;
		this.salary = salary;
		this.entryDate = entryDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long employeeId) {
		this.id = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
	
}
