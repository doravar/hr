package hu.webuni.hr.doravar.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.doravar.dto.CompanyDto;
import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.mapper.CompanyMapper;
import hu.webuni.hr.doravar.repository.CompanyRepository;
import hu.webuni.hr.doravar.service.CompanyService;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyControllerIT {

	@Autowired
	CompanyService companyService;

	@Autowired
	CompanyController companyController;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	CompanyMapper companyMapper;

	@Test
	void testAddNewEmployee() throws Exception {
		CompanyDto company = new CompanyDto(111L, "AlfaTech", "Lisboa");
		EmployeeDto employee1 = new EmployeeDto("Employee Name1", 100_000, LocalDate.of(2010, 01, 01));
		EmployeeDto employee2 = new EmployeeDto("Employee Name2", 200_000, LocalDate.of(2013, 02, 01));
		Long id = companyController.createCompany(company).getId();
		companyController.addNewEmployee(id, employee1);
		companyController.addNewEmployee(id, employee2);
		CompanyDto savedCompany = companyController.getById(id, true);
		assertThat(savedCompany.getEmployees().size()).isEqualTo(2);
	}

	@Test
	void testReplaceAllEmployee() throws Exception {
		CompanyDto company = new CompanyDto(111L, "AlfaTech", "Lisboa");
		EmployeeDto employee1 = new EmployeeDto("Employee Name1", 100_000, LocalDate.of(2010, 01, 01));
		EmployeeDto employee2 = new EmployeeDto("Employee Name2", 200_000, LocalDate.of(2013, 02, 01));
		Long id = companyController.createCompany(company).getId();
		companyController.addNewEmployee(id, employee1);
		companyController.addNewEmployee(id, employee2);
		EmployeeDto employee3 = new EmployeeDto("Employee Name3", 300_000, LocalDate.of(2010, 03, 01));
		EmployeeDto employee4 = new EmployeeDto("Employee Name4", 400_000, LocalDate.of(2013, 04, 01));
		List<EmployeeDto> newListOfEmployees = List.of(employee3, employee4);
		companyController.replaceEmployees(id, newListOfEmployees);
		List<EmployeeDto> savedEmployees = companyController.getById(id, true).getEmployees();

		assertThat(savedEmployees).isEqualTo(newListOfEmployees);

	}

	@Test
	void testDeleteEmployee() throws Exception {
		CompanyDto company = new CompanyDto(111L, "AlfaTech", "Lisboa");
		EmployeeDto employee1 = new EmployeeDto("Employee Name1", 100_000, LocalDate.of(2010, 01, 01));
		EmployeeDto employee2 = new EmployeeDto("Employee Name2", 200_000, LocalDate.of(2013, 02, 01));
		EmployeeDto employee3 = new EmployeeDto("Employee Name3", 300_000, LocalDate.of(2010, 03, 01));
		Long idOfCompany = companyController.createCompany(company).getId();
		companyController.addNewEmployee(idOfCompany, employee1);
		companyController.addNewEmployee(idOfCompany, employee2);
		companyController.addNewEmployee(idOfCompany, employee3);

		List<EmployeeDto> listOfEmployeesBeforeDeleting = companyController.getById(idOfCompany, true).getEmployees();

		long idOfEmployee = listOfEmployeesBeforeDeleting.get(0).getId();

		companyController.deleteEmployee(idOfCompany, idOfEmployee);

		List<EmployeeDto> listOfEmployeesAfterDeleting = companyController.getById(idOfCompany, true).getEmployees();

		assertThat(listOfEmployeesAfterDeleting.contains(listOfEmployeesBeforeDeleting.get(0))).isFalse();
		assertThat(listOfEmployeesAfterDeleting.size()).isEqualTo(listOfEmployeesBeforeDeleting.size() - 1);
	}
}
