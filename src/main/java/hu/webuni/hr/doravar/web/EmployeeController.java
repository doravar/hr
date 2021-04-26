package hu.webuni.hr.doravar.web;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.mapper.EmployeeMapper;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.repository.EmployeeRepository;
import hu.webuni.hr.doravar.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")

public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	EmployeeRepository employeeRepository;

	Pageable pageWithTwoElements = PageRequest.of(0, 2);
	

	@GetMapping
	public List<EmployeeDto> getEmployees(@RequestParam(required = false) Integer minSalary){
		if(minSalary == null) {
			return employeeMapper.employeesToDtos(employeeService.findAll());
		} else {
			return employeeMapper.employeesToDtos(employeeRepository.findBySalaryGreaterThan(minSalary));
		}
	}
	
//	@GetMapping
//	public Page<EmployeeDto> getEmployees(){
//			return employeeRepository.findAll(pageWithTwoElements);
//	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
		EmployeeDto employeeDto = employeeMapper.employeeToDto(
				employeeService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
		if (employeeDto != null)
			return ResponseEntity.ok(employeeDto);
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,
			@RequestBody @Valid EmployeeDto employeeDto, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Employee updatedEmployee = employeeService.update(employeeMapper.dtoToEmployee(employeeDto));
		if(updatedEmployee == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(employeeMapper.employeeToDto(updatedEmployee));
		}
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable long id) {
		try {
			employeeService.deleteById(id);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/payRaise")
	public Integer calculatingpayRaisePercent(@RequestBody @Valid Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}

	@PostMapping("/listByJobTitle")
	public List<EmployeeDto> getByJobTitle(@RequestParam String jobTitle) {
		return employeeMapper.employeesToDtos(employeeService.findByJobTitle(jobTitle));
	}

	@PostMapping("/listByNameFragment")   
	public List<EmployeeDto> getByNameFragment(@RequestParam String nameFragment) {
		return employeeMapper.employeesToDtos(employeeService.findByNameStartingWith(nameFragment));
	}

	@PostMapping("/listByEntryDate")   
	public List<EmployeeDto> getByEntryDate(@RequestBody LocalDate start, @RequestBody LocalDate end) {
		return employeeMapper.employeesToDtos(employeeService.findByEntryDateBetween(start, end));
	}
	
//	@PostMapping("/listByAvgSalary")
//	public List<Object[]> getByAvgSalary(@RequestParam long companyId) {
//			return employeeService.countAvgSalaryByJobtitle(companyId);
//		
//	}
	
	@PostMapping("/listByAvgSalary")
	public String getByAvgSalary(@RequestParam long companyId) {
		List<Object[]> result = employeeService.countAvgSalaryByJobtitle(companyId);
		StringBuilder sb = new StringBuilder();
		for (Object[] objects : result) {
			sb.append(objects[1].toString());
			sb.append(": ");			
			sb.append(objects[0].toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
	
	
	
	
}
