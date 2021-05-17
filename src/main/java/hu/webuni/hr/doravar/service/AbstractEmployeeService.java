package hu.webuni.hr.doravar.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.model.Position;
import hu.webuni.hr.doravar.repository.CompanyRepository;
import hu.webuni.hr.doravar.repository.EmployeeRepository;
import hu.webuni.hr.doravar.repository.PositionRepository;

public abstract class AbstractEmployeeService implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PositionRepository positionRepository;

	@Autowired
	CompanyRepository companyRepository;

	@Transactional
	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Transactional
	public Employee save(Employee employee, String positionName, String companyName) {
		Position Position = positionRepository.findByName(positionName).isEmpty()
				? positionRepository.save(new Position(positionName))
				: positionRepository.findByName(positionName).get();
		employee.setPosition(Position);
		Company company = companyRepository.findByName(companyName).isEmpty()
				? companyRepository.save(new Company(companyName))
				: companyRepository.findByName(companyName).get();
		employee.setCompany(company);
		return employeeRepository.save(employee);
	}

	@Transactional
	public Employee update(Employee employee) {
		if (!employeeRepository.existsById(employee.getId()))
			return null;
		else {
			String positionName = employee.getPosition().getName();
			Position position = positionRepository.findByName(positionName).isEmpty()
					? positionRepository.save(new Position(positionName))
					: positionRepository.findByName(positionName).get();
			employee.setPosition(position);
			String companyName = employee.getCompany().getName();
			Company company = companyRepository.findByName(companyName).isEmpty()
					? companyRepository.save(new Company(companyName))
					: companyRepository.findByName(companyName).get();
			employee.setCompany(company);
		}
		return employeeRepository.save(employee);
	}

	public Page<Employee> findAll(Pageable pageable) {
		return employeeRepository.findAll(pageable);
	}

	public List<Employee> findAll() {
		return employeeRepository.findAllByOrderByIdAsc();
	}

	public Optional<Employee> findById(Long id) {
		return employeeRepository.findById(id);
	}

	@Transactional
	public void deleteById(Long id) {
		if (employeeRepository.existsById(id))
			employeeRepository.deleteById(id);
		else
			throw new NoSuchElementException();
	}

	public List<Employee> findByJobTitle(String positionName) {
		return employeeRepository.findByPositionName(positionName);
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

	@Transactional
	public Employee addPosition(Long id, String positionName) {
		Position Position = positionRepository.findByName(positionName).isEmpty()
				? positionRepository.save(new Position(positionName))
				: positionRepository.findByName(positionName).get();
		Employee employee = employeeRepository.findById(id).get();
		employee.setPosition(Position);
		return employeeRepository.save(employee);
	}

	public List<Employee> findEmployeesByExample(Employee example) {
		Long id = example.getId();
		String name = example.getName();
		Position position = example.getPosition();
		String positionName = position == null ? null : position.getName();
		int salary = example.getSalary();
		LocalDate entryDate = example.getEntryDate();
		Company company = example.getCompany();
		String companyName = company == null ? null : company.getName();
		Specification<Employee> spec = Specification.where(null);
		if (id != null)
			spec = spec.and(EmployeeSpecifications.hasId(id));
		if (StringUtils.hasText(name))
			spec = spec.and(EmployeeSpecifications.hasName(name));
		if (StringUtils.hasText(positionName))
			spec = spec.and(EmployeeSpecifications.hasPosition(positionName));
		if (salary > 0)
			spec = spec.and(EmployeeSpecifications.hasSalary(salary));
		if (entryDate != null)
			spec = spec.and(EmployeeSpecifications.hasEntryDate(entryDate));
		if (StringUtils.hasText(companyName))
			spec = spec.and(EmployeeSpecifications.hasCompany(companyName));
		return employeeRepository.findAll(spec, Sort.by("id"));
	}
}
