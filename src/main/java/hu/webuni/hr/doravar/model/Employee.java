package hu.webuni.hr.doravar.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	private int salary;
	private LocalDate entryDate;

	@ManyToOne
	private Company company;
	
	@ManyToOne
	private Position position;

	public Employee() {
	}

	public Employee(Long id, String name, Position position, int salary, LocalDate entryDate) {
		this.id = id;
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.entryDate = entryDate;
	}

	public Employee(String name, Position position, int salary, LocalDate entryDate) {
		this.name = name;
		this.position = position;
		this.salary = salary;
		this.entryDate = entryDate;
	}

	public Employee(int salary, LocalDate entryDate) {
		this.salary = salary;
		this.entryDate = entryDate;
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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
