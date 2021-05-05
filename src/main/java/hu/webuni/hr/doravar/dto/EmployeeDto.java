package hu.webuni.hr.doravar.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import hu.webuni.hr.doravar.model.Position;

public class EmployeeDto {

	private Long id;
	@NotEmpty(message = "name has to be present")
	private String name;
	@Min(value = 0, message = "salary has to be positive") // @Positive is lehet
	private int salary;
	@Past(message = "entryDate has to be past")
	private LocalDate entryDate;

	private String companyName;
	@NotEmpty(message = "position has to be present")
	private String positionName;

	public EmployeeDto() {
	}

	public EmployeeDto(Long id, String name, String positionName, int salary, LocalDate entryDate) {
		this.id = id;
		this.name = name;
		this.positionName = positionName;
		this.salary = salary;
		this.entryDate = entryDate;
	}

	public EmployeeDto(int salary, LocalDate entryDate) {
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
}
