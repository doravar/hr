package hu.webuni.hr.doravar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.CompanyType;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.repository.CompanyRepository;
import hu.webuni.hr.doravar.repository.EmployeeRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	public Company save(Company company) {
		return companyRepository.save(company);
	}

	public Company update(Company company) {
		if (!companyRepository.existsById(company.getId()))
			return null;
		else
			return companyRepository.save(company);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}

	public List<Company> findAllWithEmployees() {
		return companyRepository.findAllWithEmployees();
	}

	public Optional<Company> findById(Long id) {
		return companyRepository.findById(id);
	}

	public Optional<Company> findByIdWithEmployees(Long id) {
//		return companyRepository.findByIdWithEmployees(id);
		return Optional.of(companyRepository.findByIdWithEmployees(id));
	}

	public void deleteById(Long id) {
		if (companyRepository.existsById(id))
			companyRepository.deleteById(id);
		else
			throw new NoSuchElementException();
	}

	public Company addEmployee(long id, Employee employee) {
		Company company = companyRepository.findById(id).get(); // NoSuchElementException keletkezik ha nincs id, ezt a
																// controller fogja kezelni
		company.addEmployee(employee); // companyt nem kell külön visszamenteni, mert a employee tartalmazza az id-ját
										// mint idegen kulcs, ebből az irányból mentődik companyba is
//		companyRepository.save(company); //cascade-del működne csak, hogy mentse az új employeet is 
		employeeRepository.save(employee);
		return company;
	}

	public Company deleteEmployee(long id, long employeeId) {
		Company company = companyRepository.findById(id).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setCompany(null); // employee-ből is töröljük a company-t, de nem töröljük az employeet
		company.getEmployees().remove(employee);
		employeeRepository.save(employee);
		return company;
	}

	public Company replaceEmployees(long id, List<Employee> employees) {
		Company company = companyRepository.findById(id).get();
		company.getEmployees().stream().forEach(e -> { // összes employee-ban null lesz a company
			e.setCompany(null);
		});
		company.getEmployees().clear();

		for (Employee emp : employees) {
			company.addEmployee(emp);
			employeeRepository.save(emp);
		}
		return company;
	}

	public List<Company> listCompaniesWhereEmployeesGetMinSalary(int minSalary) {
		return companyRepository.findCompaniesWhereEmployeesGetMinSalary(minSalary);
	}

	public List<Company> listCompaniesWhereNumberOfEmployeesExceeds(int limit) {
		return companyRepository.findCompaniesWhereNumberOfEmployeesExceeds(limit);
	}

	// ezt még be kell fejezni, mentse el az újat is
	public Company addCompanyType(Long id, CompanyType companyType) {
		Company company = companyRepository.findById(id).get();
		company.setCompanyType(companyType);
		return companyRepository.save(company);
	}

}
