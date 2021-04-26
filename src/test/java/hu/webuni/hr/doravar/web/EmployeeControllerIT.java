package hu.webuni.hr.doravar.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.doravar.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIT {

	private static final String BASE_URI = "/api/employees";

	@Autowired
	WebTestClient webTestClient;

	@Test
	void testThatCreatedEmployeeIsListed() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(0L, "Borzas Füzi", "student", 200_000, LocalDate.of(2019, 01, 01));
		createEmployee(newEmployee).expectStatus().isOk();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter.subList(0, employeesBefore.size())).hasSameSizeAs(employeesBefore);

		assertThat(employeesAfter.get(employeesAfter.size() - 1)).usingRecursiveComparison().ignoringFields("id")
				.isEqualTo(newEmployee);
	}

	@Test
	void testThatEmployeeWithoutNameCannotBeCreated() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(2L, "", "student", 200_000, LocalDate.of(2019, 01, 01));

		createEmployee(newEmployee).expectStatus().isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatEmployeeWithNegativeSalaryCannotBeCreated() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(3L, "Borzas Füzi", "student", -20_000, LocalDate.of(2019, 01, 01));

		createEmployee(newEmployee).expectStatus().isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatEmployeeWithFutureStartOfWorkDateCannotBeCreated() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(4L, "Borzas Füzi", "student", 200_000, LocalDate.of(2022, 01, 01));
		createEmployee(newEmployee);

		createEmployee(newEmployee).expectStatus().isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatValidEmployeeCanBeModified() throws Exception {
		EmployeeDto newEmployee = new EmployeeDto(1L, "Borzas Füzi", "student", 200_000, LocalDate.of(2019, 01, 01));
		EmployeeDto savedEmployee = createEmployee(newEmployee).expectStatus().isOk().expectBody(EmployeeDto.class)
				.returnResult().getResponseBody();

		List<EmployeeDto> employeesBefore = getAllEmployees();
		savedEmployee.setName("modifiedname");

		modifyEmployee(savedEmployee).expectStatus().isOk();
		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
		assertThat(getEmployeeById(employeesAfter, savedEmployee.getId())).usingRecursiveComparison()
				.isEqualTo(savedEmployee);

	}

	@Test
	void testThatEmployeeWithNonExistingIdCannotBeModified() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(1000L, "Borzas Füzi", "student", 200_000, LocalDate.of(2019, 01, 01));
		modifyEmployee(newEmployee).expectStatus().isNotFound();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatEmployeeCannotBeModifiedWithEmptyName() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(1L, "", "student", 200_000, LocalDate.of(2019, 01, 01));
		modifyEmployee(newEmployee).expectStatus().isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatEmployeeCannotBeModifiedWithNegativeSalary() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(1L, "Borzas Füzi", "student", -200_000, LocalDate.of(2019, 01, 01));
		modifyEmployee(newEmployee).expectStatus().isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatEmployeeCannotBeModifiedWithFutureStartOfWork() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(1L, "Borzas Füzi", "student", 200_000, LocalDate.of(2022, 01, 01));
		modifyEmployee(newEmployee).expectStatus().isBadRequest();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesAfter).hasSameSizeAs(employeesBefore);
	}

	@Test
	void testThatEmployeeWithExistingIdCanDelete() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto employeeForDelete = employeesBefore.stream().findFirst().get();
		deleteEmployee(employeeForDelete).expectStatus().isOk();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesBefore.subList(0, employeesBefore.size() - 1)).hasSameSizeAs(employeesAfter);
	}

	@Test
	void testThatEmployeeWithNonExistingIdCannotDelete() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto newEmployee = new EmployeeDto(100L, "Borzas Füzi", "student", 200_000, LocalDate.of(2019, 01, 01));
		deleteEmployee(newEmployee).expectStatus().isNotFound();

		List<EmployeeDto> employeesAfter = getAllEmployees();

		assertThat(employeesBefore).hasSameSizeAs(employeesAfter);
	}

	private EmployeeDto getEmployeeById(List<EmployeeDto> listOfEmployees, Long id) {
		return listOfEmployees.stream().filter(e -> e.getId().equals(id)).findFirst().get();
	}

	private ResponseSpec createEmployee(EmployeeDto employee) {
		return webTestClient.post().uri(BASE_URI).bodyValue(employee).exchange();
	}

	private ResponseSpec modifyEmployee(EmployeeDto employee) {
		String path = BASE_URI + "/" + employee.getId();
		return webTestClient.put().uri(path).bodyValue(employee).exchange();
	}

	private ResponseSpec deleteEmployee(EmployeeDto employee) {
		String path = BASE_URI + "/" + employee.getId();
		return webTestClient.delete().uri(path).exchange();
	}

	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> responseList = webTestClient.get().uri(BASE_URI).exchange().expectStatus().isOk()
				.expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
		Collections.sort(responseList, (a1, a2) -> Long.compare(a1.getId(), a2.getId()));
		return responseList;
	}

}
