package hu.webuni.hr.doravar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.repository.EmployeeRepository;
import hu.webuni.hr.doravar.repository.PositionDetailsByCompanyRepository;
import hu.webuni.hr.doravar.repository.PositionRepository;

@Service
public class SalaryService {

	private EmployeeService employeeService;
	private PositionRepository positionRepository;
	private PositionDetailsByCompanyRepository positionDetailsByCompanyRepository;
	private EmployeeRepository employeeRepository;

	public SalaryService(EmployeeService employeeService, PositionRepository positionRepository,
			PositionDetailsByCompanyRepository positionDetailsByCompanyRepository,
			EmployeeRepository employeeRepository) {
		this.employeeService = employeeService;
		this.positionRepository = positionRepository;
		this.positionDetailsByCompanyRepository = positionDetailsByCompanyRepository;
		this.employeeRepository = employeeRepository;
	}

	public int getNewSalary(Employee employee) {
		return employee.getSalary() * (100 + employeeService.getPayRaisePercent(employee)) / 100;
	}

//	@Transactional
//	public void raiseMinimalSalary(String positionName, int minSalary) {
//		positionRepository.findByName(positionName)
//		.forEach(p ->{
//			p.setMinSalary(minSalary);
//			p.getEmployees().forEach(e ->{
//				if(e.getSalary() < minSalary)
//					e.setSalary(minSalary);
//			});
//		});
//	}
	
	@Transactional
	public void raiseMinimalSalary(String positionName, int minSalary, long companyId) {
		positionDetailsByCompanyRepository.findByPositionNameAndCompanyId(positionName, companyId)
		.forEach(pd ->{
			pd.setMinSalary(minSalary);
//			pd.getCompany().getEmployees().forEach(e ->{		// ehelyett lett az emp.repo-ban updateSalaries, de az összes emp-t be kell tölteni hozz
//				if(e.getPosition().getName().equals(positionName)
//						&& e.getSalary() < minSalary)
//					e.setSalary(minSalary);
//			});
		});
		
		employeeRepository.updateSalaries(positionName, minSalary, companyId);
	}
	
	
}
