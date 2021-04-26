package hu.webuni.hr.doravar.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Position {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Education requiredEducation;
	private int minSalary;

	public Position() {
	}

	public Position(Long id, String name, Education requiredEducation, int minSalary) {
		this.id = id;
		this.name = name;
		this.requiredEducation = requiredEducation;
		this.minSalary = minSalary;
	}

	public Position(String name, Education requiredEducation, int minSalary) {
		this.name = name;
		this.requiredEducation = requiredEducation;
		this.minSalary = minSalary;
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

	public int getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}

}
