package hu.webuni.hr.doravar.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Education;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.model.Position;
import hu.webuni.hr.doravar.repository.CompanyRepository;
import hu.webuni.hr.doravar.repository.EmployeeRepository;
import hu.webuni.hr.doravar.repository.PositionRepository;

@Service
public class InitDbService {

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	PositionRepository positionRepository;

	public void clearDB() {
		companyRepository.deleteAll();
		employeeRepository.deleteAll();
		positionRepository.deleteAll();
		}

	public void insertTestData() {

		Position position1 = new Position("programmer", Education.BA, 500_000);
		Position position2 = new Position("manager", Education.MA, 800_000);
		Position position3 = new Position("tester", Education.HIGHSCHOOL, 300_000);
		
		Employee employee1 = new Employee("Employee Name1", position1, 100_000, LocalDate.of(2010, 01, 01));
		Employee employee2 = new Employee("Employee Name2", position1, 200_000, LocalDate.of(2013, 02, 01));
		Employee employee3 = new Employee("Employee Name3", position1, 300_000, LocalDate.of(2015, 01, 01));
		Employee employee4 = new Employee("Employee Name4", position2, 400_000, LocalDate.of(2018, 01, 01));
		Employee employee5 = new Employee("Employee Name5", position2, 500_000, LocalDate.of(2020, 01, 01));
		Employee employee6 = new Employee("Employee Name6", position3, 500, LocalDate.of(2021, 01, 01));

		Company company1 = new Company(111, "Company1", "adress 1");
		Company company2 = new Company(222, "Company2", "adress 2");
		Company company3 = new Company(333, "Company3", "adress 3");

		positionRepository.save(position1); 
		positionRepository.save(position2); 
		positionRepository.save(position3); 
		
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
