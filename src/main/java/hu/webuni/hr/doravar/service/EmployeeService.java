package hu.webuni.hr.doravar.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import hu.webuni.hr.doravar.model.Employee;

public interface EmployeeService {

	int getPayRaisePercent(Employee employee);

	Employee save(Employee employee);

	Employee update(Employee employee);

	List<Employee> findAll();

	Page<Employee> findAll(Pageable pageable);

	Optional<Employee> findById(Long id);

	void deleteById(Long id);

	List<Employee> findByJobTitle(String positionName);
	
	List<Employee> findByNameStartingWith(String fragment);

	List<Employee> findByEntryDateBetween(LocalDate start, LocalDate end);

	List<Object[]> countAvgSalaryByJobtitle(Long companyId);
	
	Employee addPosition(Long id, String positionName);
	
	List<Employee> findEmployeesByExample(Employee example);
}
