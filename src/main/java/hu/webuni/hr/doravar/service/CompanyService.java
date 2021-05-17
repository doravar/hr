package hu.webuni.hr.doravar.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.CompanyType;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.model.Position;
import hu.webuni.hr.doravar.repository.CompanyRepository;
import hu.webuni.hr.doravar.repository.CompanyTypeRepository;
import hu.webuni.hr.doravar.repository.EmployeeRepository;
import hu.webuni.hr.doravar.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.doravar.repository.PositionRepository;

@Service
public class CompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PositionRepository positionRepository;

	@Autowired
	CompanyTypeRepository companyTypeRepository;

	@Autowired
	PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;

	@Transactional
	public Company save(Company company) {
		return companyRepository.save(company);
	}

	@Transactional
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

	@Transactional
	public void deleteById(Long id) {
		if (companyRepository.existsById(id)) {
			List<Employee> employees = companyRepository.findEmployees(id);
			for (Employee employee : employees) {
				employee.setCompany(null);
				employeeRepository.save(employee);
			}
			positionDetailsByCompanyRepository.deleteByCompany(companyRepository.findById(id).get());
			companyRepository.deleteById(id);
		} else
			throw new NoSuchElementException();
	}

	@Transactional
	public Company addEmployee(long id, Employee employee, String positionName) {
		Company company = companyRepository.findWithEmployeesById(id).get(); // NoSuchElementException keletkezik ha
																				// nincs id, ezt a
		// controller fogja kezelni
		Position position = positionRepository.findByName(positionName).isEmpty()
				? positionRepository.save(new Position(positionName))
				: positionRepository.findByName(positionName).get();
		employee.setPosition(position);
		company.addEmployee(employee); // companyt nem kell külön visszamenteni, mert a employee tartalmazza az id-ját
										// mint idegen kulcs, ebből az irányból mentődik companyba is
//		companyRepository.save(company); //cascade-del működne csak, hogy mentse az új employeet is 
		employeeRepository.save(employee);
		return company;
	}

	@Transactional
	public Company deleteEmployee(long id, long employeeId) {
		Company company = companyRepository.findById(id).get();
		Employee employee = employeeRepository.findById(employeeId).get();
		employee.setCompany(null); // employee-ből is töröljük a company-t, de nem töröljük az employeet
		company.getEmployees().remove(employee);
		employeeRepository.save(employee);
		return company;
	}

	@Transactional
	public Company replaceEmployees(long id, List<Employee> employees) {
		Company company = companyRepository.findWithEmployeesById(id).get();
		company.getEmployees().stream().forEach(e -> { // összes employee-ban null lesz a company
			e.setCompany(null);
		});
		company.getEmployees().clear();
		for (Employee emp : employees) {
			String positionName = emp.getPosition().getName();
			Position Position = positionRepository.findByName(positionName).isEmpty()
					? positionRepository.save(new Position(positionName))
					: positionRepository.findByName(positionName).get();
			emp.setPosition(Position);
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

	@Transactional
	public Company addCompanyType(Long id, String companyTypeName) {
		CompanyType companyType = companyTypeRepository.findByName(companyTypeName).isEmpty()
				? companyTypeRepository.save(new CompanyType(companyTypeName))
				: companyTypeRepository.findByName(companyTypeName).get();
		Company company = companyRepository.findById(id).get();
		company.setCompanyType(companyType);
		return companyRepository.save(company);
	}

}
