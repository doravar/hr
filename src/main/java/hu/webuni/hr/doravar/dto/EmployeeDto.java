package hu.webuni.hr.doravar.dto;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;

public class EmployeeDto {

	private Long id;
	@NotEmpty(message = "name has to be present")
	private String name;
	@NotEmpty(message = "job title has to be present")
	private String jobTitle;
	@Min(value = 0, message = "salary has to be positive") // @Positive is lehet
	private int salary;
	@Past(message = "entryDate has to be past")
	private LocalDate entryDate;

	private String companyName;

	public EmployeeDto() {
	}

	public EmployeeDto(Long employeeId, String name, String jobTitle, int salary, LocalDate entryDate) {
		this.id = employeeId;
		this.name = name;
		this.jobTitle = jobTitle;
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

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
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

	@Override
	public String toString() {
		return "EmployeeDto [id=" + id + ", name=" + name + ", jobTitle=" + jobTitle + ", salary=" + salary
				+ ", entryDate=" + entryDate + "]";
	}

}
