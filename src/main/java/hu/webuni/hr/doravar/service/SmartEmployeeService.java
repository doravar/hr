package hu.webuni.hr.doravar.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.doravar.config.HrConfigProperties;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.repository.EmployeeRepository;

@Service
public class SmartEmployeeService extends AbstractEmployeeService {

	public SmartEmployeeService() {
	}

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		long months = java.time.temporal.ChronoUnit.MONTHS.between(employee.getEntryDate(), LocalDate.now());
		return config.getSalary().getSmart().getRaisingIntervals().get(config.getSalary().getSmart()
				.getRaisingIntervals().keySet().stream().filter(k -> months >= k).max(Integer::compare).get());

	}

}
