package hu.webuni.hr.doravar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.doravar.config.HrConfigProperties;
import hu.webuni.hr.doravar.model.Employee;

@Service
public class DefaultEmployeeService extends AbstractEmployeeService {

	public DefaultEmployeeService() {
	}

	@Autowired
	HrConfigProperties config;

	@Override
	public int getPayRaisePercent(Employee employee) {
		return config.getSalary().getDef().getPercent();
	}

}
