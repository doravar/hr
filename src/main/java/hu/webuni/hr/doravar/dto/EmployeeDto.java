package hu.webuni.hr.doravar.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

import hu.webuni.hr.doravar.model.Employee;

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

	private String username;

	private EmployeeDto manager;

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
		this.id = id;
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

	public EmployeeDto getManager() {
		return manager;
	}

	public void setManager(EmployeeDto manager) {
		this.manager = manager;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entryDate == null) ? 0 : entryDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((positionName == null) ? 0 : positionName.hashCode());
		result = prime * result + salary;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDto other = (EmployeeDto) obj;
		if (entryDate == null) {
			if (other.entryDate != null)
				return false;
		} else if (!entryDate.equals(other.entryDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (positionName == null) {
			if (other.positionName != null)
				return false;
		} else if (!positionName.equals(other.positionName))
			return false;
		if (salary != other.salary)
			return false;
		return true;
	}

}
