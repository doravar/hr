package hu.webuni.hr.doravar.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Holiday {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private Employee employee;

	@ManyToOne
	private Employee approver;

	private StateOfHolidayRequest stateOfHolidayRequest;

	private LocalDate dateOfSubmission;
	private LocalDate startOfHoliday;
	private LocalDate endOfHoliday;

	public Holiday() {
	}

	public Holiday(long id, Employee employee, Employee approver, StateOfHolidayRequest stateOfHolidayRequest,
			LocalDate startOfHoliday, LocalDate endOfHoliday) {
		this.id = id;
		this.employee = employee;
		this.approver = approver;
		this.stateOfHolidayRequest = stateOfHolidayRequest;
		this.startOfHoliday = startOfHoliday;
		this.endOfHoliday = endOfHoliday;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public Employee getApprover() {
		return approver;
	}

	public void setApprover(Employee approver) {
		this.approver = approver;
	}
	
	

}
