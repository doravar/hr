package hu.webuni.hr.doravar.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.repository.EmployeeRepository;

public abstract class AbstractEmployeeService implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Employee update(Employee employee) {
		if (!employeeRepository.existsById(employee.getId()))
			return null;
		else
			return employeeRepository.save(employee);
	}

	public Page<Employee> findAll(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	public void deleteById(Long id) {
		if (employeeRepository.existsById(id))
			employeeRepository.deleteById(id);
		else
			throw new NoSuchElementException();
	}

	public List<Employee> findByJobTitle(String jobTitle) {
		return employeeRepository.findByJobTitle(jobTitle);
	}

	public List<Employee> findByNameStartingWith(String fragment) {
		return employeeRepository.findByNameStartingWithIgnoreCase(fragment);
	}

	public List<Employee> findByEntryDateBetween(LocalDate start, LocalDate end) {
		return employeeRepository.findByEntryDateBetween(start, end);
	}

	public List<Object[]> countAvgSalaryByJobtitle(Long companyId) {
		return employeeRepository.countAvgSalaryByJobtitle(companyId);
	}

}
