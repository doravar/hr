package hu.webuni.hr.doravar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.doravar.service.SalaryService;


@RequestMapping("/api/salary")
@RestController
public class SalaryController {

	@Autowired
	SalaryService salaryService;
	
	@PutMapping(params = {"positionName", "minSalary", "companyId"})
	public void raiseMinSalary(String positionName, int minSalary, long companyId) {
		salaryService.raiseMinimalSalary(positionName, minSalary, companyId);
//		salaryService.raiseMinimalSalary(positionName, minSalary, companyId);
	}
}
