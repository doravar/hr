package hu.webuni.hr.doravar.dto;

import java.time.LocalDate;

import hu.webuni.hr.doravar.model.StateOfHolidayRequest;

public class HolidayRequestFilterDto { // a kereséskor megadott dto

	private LocalDate startOfSubmissionPeriod;
	private LocalDate endOfSubmissionPeriod;
	private String employeeName;
	private String approverName;
	private StateOfHolidayRequest stateOfHolidayRequest;
	private LocalDate startOfHoliday;
	private LocalDate endOfHoliday;

	public HolidayRequestFilterDto() {
	}

	public LocalDate getStartOfSubmissionPeriod() {
		return startOfSubmissionPeriod;
	}

	public void setStartOfSubmissionPeriod(LocalDate startOfSubmissionPeriod) {
		this.startOfSubmissionPeriod = startOfSubmissionPeriod;
	}

	public LocalDate getEndOfSubmissionPeriod() {
		return endOfSubmissionPeriod;
	}

	public void setEndOfSubmissionPeriod(LocalDate endOfSubmissionPeriod) {
		this.endOfSubmissionPeriod = endOfSubmissionPeriod;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public StateOfHolidayRequest getStateOfHolidayRequest() {
		return stateOfHolidayRequest;
	}

	public void setStateOfHolidayRequest(StateOfHolidayRequest stateOfHolidayRequest) {
		this.stateOfHolidayRequest = stateOfHolidayRequest;
	}

	public LocalDate getStartOfHoliday() {
		return startOfHoliday;
	}

	public void setStartOfHoliday(LocalDate startOfHoliday) {
		this.startOfHoliday = startOfHoliday;
	}

	public LocalDate getEndOfHoliday() {
		return endOfHoliday;
	}

	public void setEndOfHoliday(LocalDate endOfHoliday) {
		this.endOfHoliday = endOfHoliday;
	}

}
