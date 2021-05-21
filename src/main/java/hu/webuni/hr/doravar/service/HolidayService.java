package hu.webuni.hr.doravar.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import hu.webuni.hr.doravar.dto.HolidayRequestFilterDto;
import hu.webuni.hr.doravar.model.Holiday;
import hu.webuni.hr.doravar.model.StateOfHolidayRequest;
import hu.webuni.hr.doravar.repository.EmployeeRepository;
import hu.webuni.hr.doravar.repository.HolidayRepository;

@Service
public class HolidayService {

	@Autowired
	HolidayRepository holidayRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Transactional
	public Holiday save(Holiday holiday) {
		return holidayRepository.save(holiday);
	}

	public List<Holiday> findAll() {
		return holidayRepository.findAll();
	}

	@Transactional
	public Holiday update(Holiday holiday) {
		if (!holidayRepository.existsById(holiday.getId()))
			return null;
		if (!holidayRepository.findById(holiday.getId()).get().getStateOfHolidayRequest()
				.equals(StateOfHolidayRequest.PENDING))
			throw new IllegalArgumentException("the holiday request has already been judged, it cannot be deleted");
		else
			return holidayRepository.save(holiday);
	}

	@Transactional
	public Holiday approve(long id, String stateOfHolidayRequest) {
		if (!holidayRepository.existsById(id))
			throw new NoSuchElementException();
		else {
			Holiday holiday = holidayRepository.findById(id).get();
			if (!holiday.getStateOfHolidayRequest().equals(StateOfHolidayRequest.PENDING))
				throw new IllegalArgumentException("the holiday request has already been judged"); // ez még nem a
																									// legszebb
			holiday.setStateOfHolidayRequest(StateOfHolidayRequest.valueOf(stateOfHolidayRequest));
			return holidayRepository.save(holiday);

		}
	}

	@Transactional
	public void deleteById(long id) {
		if (!holidayRepository.existsById(id))
			throw new NoSuchElementException();
		if (!holidayRepository.findById(id).get().getStateOfHolidayRequest().equals(StateOfHolidayRequest.PENDING))
			throw new IllegalArgumentException("the holiday request has already been judged, it cannot be deleted");
		else {
			holidayRepository.deleteById(id);
		}

	}

	public Page<Holiday> findHolidaysByExample(HolidayRequestFilterDto example, Pageable pageable) {
		StateOfHolidayRequest stateOfHolidayRequest = example.getStateOfHolidayRequest();
		String employeeName = example.getEmployeeName();
		String approverName = example.getApproverName();
		LocalDate startOfSubmissionPeriod = example.getStartOfSubmissionPeriod();
		LocalDate endOfSubmissionPeriod = example.getEndOfSubmissionPeriod();
		LocalDate startOfHoliday = example.getStartOfHoliday();
		LocalDate endOfHoliday = example.getEndOfHoliday();

		Specification<Holiday> spec = Specification.where(null);
		if (stateOfHolidayRequest != null)
			spec = spec.and(HolidaySpecifications.hasStateOfHolidayRequest(stateOfHolidayRequest));
		if (StringUtils.hasText(employeeName))
			spec = spec.and(HolidaySpecifications.hasEmployeeName(employeeName));
		if (StringUtils.hasText(approverName))
			spec = spec.and(HolidaySpecifications.hasApproverName(approverName));
		if (startOfSubmissionPeriod != null && endOfSubmissionPeriod != null)
			spec = spec.and(
					HolidaySpecifications.dateOfSubmissionIsBetween(startOfSubmissionPeriod, endOfSubmissionPeriod));
		if (startOfHoliday != null)
			spec = spec.and(HolidaySpecifications.isStartDateLessThan(startOfHoliday));
		if (endOfHoliday != null)
			spec = spec.and(HolidaySpecifications.isEndDateGreaterThan(endOfHoliday));
		return holidayRepository.findAll(spec, pageable);
	}
}
