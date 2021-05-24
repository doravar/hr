package hu.webuni.hr.doravar.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.doravar.dto.CompanyDto;
import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.dto.HolidayDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CompanyControllerIT {

	private static final String BASE_URI = "/api/companies";

	@Autowired
	WebTestClient webTestClient;

	@Test
	void testAddNewEmployee() throws Exception {
		CompanyDto company = new CompanyDto(111L, "AlfaTech", "Lisboa");
		CompanyDto savedCompanyBefore = createCompany(company).expectStatus().isOk().expectBody(CompanyDto.class)
				.returnResult().getResponseBody();
		EmployeeDto employee1 = new EmployeeDto("Employee Name1", 100_000, LocalDate.of(2010, 01, 01));
		Long id = savedCompanyBefore.getId();
		List<EmployeeDto> listEmployeesBefore = savedCompanyBefore.getEmployees();
		addNewEmployee(id, employee1);
		CompanyDto savedCompanyAfter = getCompanyByIdWithEmployees(id);
		List<EmployeeDto> listEmployeesAfter = savedCompanyAfter.getEmployees();
		assertThat(listEmployeesBefore.size()).isEqualTo(listEmployeesAfter.size() - 1);
		assertThat(listEmployeesAfter.contains(employee1));
	}

	@Test
	void testReplaceAllEmployee() throws Exception {
		CompanyDto company = new CompanyDto(111L, "AlfaTech", "Lisboa");
		long id = createCompany(company).expectStatus().isOk().expectBody(CompanyDto.class).returnResult()
				.getResponseBody().getId();
		EmployeeDto employee1 = new EmployeeDto("Employee Name1", 100_000, LocalDate.of(2010, 01, 01));
		EmployeeDto employee2 = new EmployeeDto("Employee Name2", 200_000, LocalDate.of(2013, 02, 01));
		addNewEmployee(id, employee1);
		addNewEmployee(id, employee2);

		EmployeeDto employee3 = new EmployeeDto("Employee Name3", 300_000, LocalDate.of(2010, 03, 01));
		EmployeeDto employee4 = new EmployeeDto("Employee Name4", 400_000, LocalDate.of(2013, 04, 01));
		List<EmployeeDto> newListOfEmployees = List.of(employee3, employee4);
		replaceEmployees(id, newListOfEmployees);

		List<EmployeeDto> savedEmployees = getCompanyByIdWithEmployees(id).getEmployees();

		assertThat(savedEmployees).usingElementComparatorIgnoringFields("id", "companyName")
				.isEqualTo(newListOfEmployees);
	}

	@Test
	void testDeleteEmployee() throws Exception {
		CompanyDto company = new CompanyDto(111L, "AlfaTech", "Lisboa");
		long companyId = createCompany(company).expectStatus().isOk().expectBody(CompanyDto.class).returnResult()
				.getResponseBody().getId();
		EmployeeDto employee1 = new EmployeeDto("Employee Name1", 100_000, LocalDate.of(2010, 01, 01));
		EmployeeDto employee2 = new EmployeeDto("Employee Name2", 200_000, LocalDate.of(2013, 02, 01));
		EmployeeDto employee3 = new EmployeeDto("Employee Name3", 300_000, LocalDate.of(2010, 03, 01));
		addNewEmployee(companyId, employee1);
		addNewEmployee(companyId, employee2);
		addNewEmployee(companyId, employee3);
		List<EmployeeDto> savedEmployeesBefore = getCompanyByIdWithEmployees(companyId).getEmployees();
		EmployeeDto deletedEmployee = savedEmployeesBefore.get(0);
		long employeeId = deletedEmployee.getId();
		deleteEmployee(companyId, employeeId);
		List<EmployeeDto> savedEmployeesAfter = getCompanyByIdWithEmployees(companyId).getEmployees();
		assertThat(savedEmployeesAfter.contains(deletedEmployee)).isFalse();
		assertThat(savedEmployeesAfter.size()).isEqualTo(savedEmployeesBefore.size() - 1);
	}

	private ResponseSpec createCompany(CompanyDto company) {
		return webTestClient.post().uri(BASE_URI).headers(headers -> headers.setBasicAuth("user1", "pass"))
				.bodyValue(company).exchange();
	}

	private ResponseSpec addNewEmployee(long id, EmployeeDto employeeDto) {
		String path = BASE_URI + "/" + id + "/addNewEmployee";
		return webTestClient.post().uri(path).headers(headers -> headers.setBasicAuth("user2", "pass"))
				.bodyValue(employeeDto).exchange();
	}

	private CompanyDto getCompanyByIdWithEmployees(long id) {
		String path = BASE_URI + "/" + id;
		return webTestClient.get().uri(uriBuilder -> uriBuilder.path(path).queryParam("full", true).build())
				.headers(headers -> headers.setBasicAuth("user1", "pass")).exchange().expectStatus().isOk()
				.expectBody(CompanyDto.class).returnResult().getResponseBody();
	}

	private ResponseSpec replaceEmployees(long id, List<EmployeeDto> employees) {
		String path = BASE_URI + "/" + id + "/replaceEmployees";
		return webTestClient.put().uri(path).headers(headers -> headers.setBasicAuth("user2", "pass"))
				.bodyValue(employees).exchange();
	}

	private CompanyDto deleteEmployee(long companyId, long employeeId) {
		String path = BASE_URI + "/" + companyId + "/employees/" + employeeId;
		return webTestClient.delete().uri(path).headers(headers -> headers.setBasicAuth("user2", "pass")).exchange()
				.expectStatus().isOk().expectBody(CompanyDto.class).returnResult().getResponseBody();
	}

}
