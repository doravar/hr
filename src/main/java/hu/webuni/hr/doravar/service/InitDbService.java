package hu.webuni.hr.doravar.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.repository.CompanyRepository;
import hu.webuni.hr.doravar.repository.EmployeeRepository;

@Service
public class InitDbService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	public void clearDB() {
		companyRepository.deleteAll();
		employeeRepository.deleteAll();
	}

	public void insertTestData() {

		Employee employee1 = new Employee("Employee Name1", "jobtitle1", 100_000, LocalDate.of(2010, 01, 01));
		Employee employee2 = new Employee("Employee Name2", "jobtitle1", 200_000, LocalDate.of(2013, 02, 01));
		Employee employee3 = new Employee("Employee Name3", "jobtitle1", 300_000, LocalDate.of(2015, 01, 01));
		Employee employee4 = new Employee("Employee Name4", "jobtitle2", 400_000, LocalDate.of(2018, 01, 01));
		Employee employee5 = new Employee("Employee Name5", "jobtitle2", 500_000, LocalDate.of(2020, 01, 01));
		Employee employee6 = new Employee("Employee Name6", "jobtitle3", 500, LocalDate.of(2021, 01, 01));

		Company company1 = new Company(111, "Company1", "adress 1");
		Company company2 = new Company(222, "Company2", "adress 2");
		Company company3 = new Company(333, "Company3", "adress 3");

		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		employeeRepository.save(employee4);
		employeeRepository.save(employee5);
		employeeRepository.save(employee6);

		companyRepository.save(company1);
		companyRepository.save(company2);
		companyRepository.save(company3);

		company1.addEmployee(employee1);
		company1.addEmployee(employee2);
		company2.addEmployee(employee3);
		company2.addEmployee(employee4);
		company2.addEmployee(employee5);
		company3.addEmployee(employee6);

		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		employeeRepository.save(employee3);
		employeeRepository.save(employee4);
		employeeRepository.save(employee5);
		employeeRepository.save(employee6);
	}

}
