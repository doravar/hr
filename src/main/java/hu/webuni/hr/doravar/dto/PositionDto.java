package hu.webuni.hr.doravar.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import hu.webuni.hr.doravar.model.Education;

public class PositionDto {

	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private Education requiredEducation;
	@Positive
	private int minSalary;

	public PositionDto(Long id, String name, Education requiredEducation, int minSalary) {
		this.id = id;
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
