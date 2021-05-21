package hu.webuni.hr.doravar.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.doravar.dto.CompanyDto;
import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.dto.HolidayDto;
import hu.webuni.hr.doravar.model.StateOfHolidayRequest;
import hu.webuni.hr.doravar.repository.HolidayRepository;
import hu.webuni.hr.doravar.service.HolidayService;

@SpringBootTest
@AutoConfigureTestDatabase
public class HolidayControllerIT {

	@Autowired
	HolidayController holidayController;

	@Test
	void testThatCreatedHolidayIsListed() throws Exception {
		HolidayDto holiday = new HolidayDto(4L, 5L, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 20));
		List<HolidayDto> listBefore = holidayController.getHolidays();
		Long id = holidayController.createHoliday(holiday).getId();
		HolidayDto savedHoliday = holidayController.getById(id);
		List<HolidayDto> listAfter = holidayController.getHolidays();
		assertThat(savedHoliday).usingRecursiveComparison().ignoringFields("id").isEqualTo(holiday);
		assertThat(listBefore.size() + 1).isEqualTo(listAfter.size());
	}

	@Test
	void testApproveHoliday() throws Exception {
		HolidayDto holiday = new HolidayDto(4L, 5L, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 20));
		Long id = holidayController.createHoliday(holiday).getId();
		HolidayDto savedHoliday = holidayController.getById(id);
		assertThat(savedHoliday.getStateOfHolidayRequest()).isEqualTo(StateOfHolidayRequest.PENDING);
		holidayController.approveHoliday(id, "APPROVED");
		savedHoliday = holidayController.getById(id);
		assertThat(savedHoliday.getStateOfHolidayRequest()).isEqualTo(StateOfHolidayRequest.APPROVED);
	}

}
