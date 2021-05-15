package hu.webuni.hr.doravar.service;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.doravar.model.Company_;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.model.Employee_;
import hu.webuni.hr.doravar.model.Position_;

public class EmployeeSpecifications {

	public static Specification<Employee> hasId(Long id) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.id), id);
	}

	public static Specification<Employee> hasName(String name) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Employee_.name)), name.toUpperCase() + "%");
	}

	public static Specification<Employee> hasPosition(String positionName) {
		return (root, cq, cb) -> cb.like(root.get(Employee_.position).get(Position_.name), positionName);
	}
	
	public static Specification<Employee> hasSalary(int salary) {
		return (root, cq, cb) -> cb.between(root.get(Employee_.salary), salary/100*95, salary/100*105);
	}
		
	public static Specification<Employee> hasEntryDate(LocalDate entryDate) {
		return (root, cq, cb) -> cb.equal(root.get(Employee_.entryDate), entryDate);
	}

	public static Specification<Employee> hasCompany(String companyName) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Employee_.company).get(Company_.name)), companyName.toUpperCase() + "%"); 
	}
	
	
}
