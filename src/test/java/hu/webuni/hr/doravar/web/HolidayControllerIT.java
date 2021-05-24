package hu.webuni.hr.doravar.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.webuni.hr.doravar.dto.CompanyDto;
import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.dto.HolidayDto;
import hu.webuni.hr.doravar.model.StateOfHolidayRequest;
import hu.webuni.hr.doravar.repository.HolidayRepository;
import hu.webuni.hr.doravar.service.HolidayService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class HolidayControllerIT {

	private static final String BASE_URI = "/api/holidays";

	@Autowired
	WebTestClient webTestClient;

	@Test
	void testThatCreatedHolidayIsListed() throws Exception {
		HolidayDto holiday = new HolidayDto(4L, 5L, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 20));
		HolidayDto savedHoliday = createHoliday(holiday).expectStatus().isOk().expectBody(HolidayDto.class)
				.returnResult().getResponseBody();
		List<HolidayDto> holidays = getHolidays();
		assertThat(holidays.contains(savedHoliday));
	}

	@Test
	void testApproveHoliday() throws Exception {
		HolidayDto holiday = new HolidayDto(4L, 5L, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 20));
		long holidayId = createHoliday(holiday).expectStatus().isOk().expectBody(HolidayDto.class).returnResult()
				.getResponseBody().getId();
		approveHoliday(holidayId, "APPROVED");
		HolidayDto savedHoliday = getById(holidayId);
		assertThat(savedHoliday.getStateOfHolidayRequest()).isEqualTo(StateOfHolidayRequest.APPROVED);
	}

	@Test
	void tesModifytHoliday() throws Exception {
		HolidayDto holiday = new HolidayDto(4L, 5L, LocalDate.of(2022, 01, 01), LocalDate.of(2022, 01, 20));
		HolidayDto savedHolidaybefore = createHoliday(holiday).expectStatus().isOk().expectBody(HolidayDto.class)
				.returnResult().getResponseBody();
		savedHolidaybefore.setEndOfHoliday(LocalDate.of(2022, 10, 20));
		modifyHoliday(savedHolidaybefore);
		HolidayDto savedHolidayAfter = getById(savedHolidaybefore.getId());
		assertThat(savedHolidayAfter.getEndOfHoliday()).isEqualTo(LocalDate.of(2022, 10, 20));
	}

	private ResponseSpec createHoliday(HolidayDto holiday) {
		return webTestClient.post().uri(BASE_URI).bodyValue(holiday).exchange();
	}

	private List<HolidayDto> getHolidays() {
		return webTestClient.get().uri(BASE_URI).exchange().expectStatus().isOk().expectBodyList(HolidayDto.class)
				.returnResult().getResponseBody();
	}

	private ResponseSpec approveHoliday(long id, String stateOfHolidayRequest) {
		String path = BASE_URI + "/approval/" + id;
		return webTestClient.put().uri(
				uriBuilder -> uriBuilder.path(path).queryParam("stateOfHolidayRequest", stateOfHolidayRequest).build())
				.exchange();
	}

	private HolidayDto getById(long id) {
		String path = BASE_URI + "/" + id;
		return webTestClient.get().uri(path).exchange().expectStatus().isOk().expectBody(HolidayDto.class)
				.returnResult().getResponseBody();
	}

	private HolidayDto modifyHoliday(HolidayDto holiday) {
		String path = BASE_URI + "/" + holiday.getId();
		return webTestClient.put().uri(path).bodyValue(holiday).exchange().expectStatus().isOk()
				.expectBody(HolidayDto.class).returnResult().getResponseBody();
	}

}
