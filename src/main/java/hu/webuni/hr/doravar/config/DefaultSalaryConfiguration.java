package hu.webuni.hr.doravar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.doravar.service.DefaultEmployeeService;
import hu.webuni.hr.doravar.service.EmployeeService;

@Configuration
@Profile("!smart")

public class DefaultSalaryConfiguration {

	@Bean
	public EmployeeService employeeService() {
		return new DefaultEmployeeService();
	}
}
