package hu.webuni.hr.doravar.dto;

import java.time.LocalDate;

import javax.validation.constraints.Future;

import com.sun.istack.NotNull;

import hu.webuni.hr.doravar.model.StateOfHolidayRequest;

public class HolidayDto {
	private long id;
	@NotNull
	private long employeeId;
	@NotNull
	private long approverId;

	private StateOfHolidayRequest stateOfHolidayRequest;

	private LocalDate dateOfSubmission;	
	@NotNull
	@Future
	private LocalDate startOfHoliday;
	@NotNull
	@Future
	private LocalDate endOfHoliday;

	{
		stateOfHolidayRequest  = StateOfHolidayRequest.PENDING;
		dateOfSubmission = LocalDate.now();
	}
	
	public HolidayDto() {
	}

	public HolidayDto(long employeeId, long approverId, LocalDate startOfHoliday,
			LocalDate endOfHoliday) {
		this.approverId = approverId;
		this.employeeId = employeeId;
		this.startOfHoliday = startOfHoliday;
		this.endOfHoliday = endOfHoliday;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}

	public StateOfHolidayRequest getStateOfHolidayRequest() {
		return stateOfHolidayRequest;
	}

	public void setStateOfHolidayRequest(StateOfHolidayRequest stateOfHolidayRequest) {
		this.stateOfHolidayRequest = stateOfHolidayRequest;
	}

	public LocalDate getDateOfSubmission() {
		return dateOfSubmission;
	}

	public void setDateOfSubmission(LocalDate dateOfSubmission) {
		this.dateOfSubmission = dateOfSubmission;
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

	public long getApproverId() {
		return approverId;
	}

	public void setApproverId(long approverId) {
		this.approverId = approverId;
	}
	
	

}
