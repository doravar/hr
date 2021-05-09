package hu.webuni.hr.doravar.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Position {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Education requiredEducation;
//	private int minSalary;
	
	@OneToMany(mappedBy = "position")
	private List<Employee> employees;

	public Position() {
	}

	public Position(String name, Education requiredEducation) {
		this.name = name;
		this.requiredEducation = requiredEducation;
//		this.minSalary = minSalary;
	}
	
	

	public Position(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Education getRequiredEducation() {
		return requiredEducation;
	}

	public void setRequiredEducation(Education requiredEducation) {
		this.requiredEducation = requiredEducation;
	}

//	public int getMinSalary() {
//		return minSalary;
//	}
//
//	public void setMinSalary(int minSalary) {
//		this.minSalary = minSalary;
//	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	
}
