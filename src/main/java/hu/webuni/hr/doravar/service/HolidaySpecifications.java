package hu.webuni.hr.doravar.service;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import hu.webuni.hr.doravar.model.Employee_;
import hu.webuni.hr.doravar.model.Holiday;
import hu.webuni.hr.doravar.model.Holiday_;
import hu.webuni.hr.doravar.model.StateOfHolidayRequest;

public class HolidaySpecifications {

	public static Specification<Holiday> hasStateOfHolidayRequest(StateOfHolidayRequest stateOfHolidayRequest) {
		return (root, cq, cb) -> cb.equal(root.get(Holiday_.stateOfHolidayRequest), stateOfHolidayRequest);
	}

	public static Specification<Holiday> hasEmployeeName(String employeeName) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Holiday_.employee).get(Employee_.name)),
				employeeName.toUpperCase() + "%");
	}

	public static Specification<Holiday> hasApproverName(String approverName) {
		return (root, cq, cb) -> cb.like(cb.upper(root.get(Holiday_.approver).get(Employee_.name)),
				approverName.toUpperCase() + "%");
	}

	public static Specification<Holiday> dateOfSubmissionIsBetween(LocalDate start, LocalDate end) {
		return (root, cq, cb) -> cb.between(root.get(Holiday_.dateOfSubmission), start, end);
	}

	public static Specification<Holiday> isStartDateLessThan(LocalDate start) {		// example start korábbi, mint a keresettek vége
		return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(Holiday_.endOfHoliday), start);
	}
	
	public static Specification<Holiday> isEndDateGreaterThan(LocalDate end) {		// example vége későbbi, mint a keresettek eleje
		return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(Holiday_.startOfHoliday), end);
	}

}
