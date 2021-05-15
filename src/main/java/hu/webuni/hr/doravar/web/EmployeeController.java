package hu.webuni.hr.doravar.web;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import hu.webuni.hr.doravar.dto.CompanyDto;
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

	Pageable pageWithTwoElements = PageRequest.of(0, 3);

	@GetMapping
	public List<EmployeeDto> getEmployees(@RequestParam(required = false) Integer minSalary) {
		if (minSalary == null) {
			return employeeMapper.employeesSummariesToDtos(employeeService.findAll());
		} else {
			return employeeMapper.employeesSummariesToDtos(employeeRepository.findBySalaryGreaterThan(minSalary));
		}
	}

//	@GetMapping("/pageable")
//	public Page<EmployeeDto> getEmployees() {
//		return employeeRepository.findAll(pageWithTwoElements).map(employeeMapper::employeeToDto);
//	}

	@GetMapping("/pageable")
	public List<EmployeeDto> getEmployees(Pageable pageable) {
		Page<Employee> pageOfEmployees = employeeRepository.findAll(pageable);
		System.out.println(pageOfEmployees.getNumber());
		System.out.println(pageOfEmployees.getNumberOfElements());
		System.out.println(pageOfEmployees.getSize());
		System.out.println(pageOfEmployees.getTotalElements());
		System.out.println(pageOfEmployees.getTotalPages());
		System.out.println(pageOfEmployees.isFirst());
		return employeeMapper.employeesToDtos(pageOfEmployees.getContent());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getById(@PathVariable long id) {
		EmployeeDto employeeDto = employeeMapper.employeeSummaryToDto(
				employeeService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
		if (employeeDto != null)
			return ResponseEntity.ok(employeeDto);
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/listByEntryDate")
	public List<EmployeeDto> getByEntryDate(@RequestParam LocalDate start, @RequestParam LocalDate end) {
		return employeeMapper.employeesToDtos(employeeService.findByEntryDateBetween(start, end));
	}

	@GetMapping(params = "jobTitle")
	public List<EmployeeDto> getByJobTitle(@RequestParam String jobTitle) {
		return employeeMapper.employeesSummariesToDtos(employeeService.findByJobTitle(jobTitle));
	}

	@GetMapping(params = "nameFragment")
	public List<EmployeeDto> getByNameFragment(@RequestParam String nameFragment) {
		return employeeMapper.employeesToDtos(employeeService.findByNameStartingWith(nameFragment));
	}

	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employeeDto,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Employee employee = employeeService.save(employeeMapper.dtoToEmployee(employeeDto));
		return ResponseEntity.ok(employeeMapper.employeeSummaryToDto(employee));
	}

	@PostMapping("/payRaise")
	public Integer calculatingpayRaisePercent(@RequestBody @Valid Employee employee) {
		return employeeService.getPayRaisePercent(employee);
	}

//	@PostMapping("/listByAvgSalary")
//	public List<Object[]> getByAvgSalary(@RequestParam long companyId) {
//			return employeeService.countAvgSalaryByJobtitle(companyId);
//		
//	}

	@PostMapping("/listByAvgSalary")
	public Map<Object, Object> getByAvgSalary(@RequestParam long companyId) {
		List<Object[]> result = employeeService.countAvgSalaryByJobtitle(companyId);
		Map<Object, Object> titleAndAndSalary = new HashMap<>();
		result.stream().forEach(o -> titleAndAndSalary.put(o[0], o[1]));
		return titleAndAndSalary;
	}

	// position és company esetében még nem működik, a dto -> entity mappelés
	// hiányai (dto-ban csak positionname) miatt
	@GetMapping("/getByExample")
	public List<EmployeeDto> findEmployeesByExample(@RequestBody EmployeeDto employeeDto) {
		return employeeMapper.employeesSummariesToDtos(
				employeeService.findEmployeesByExample(employeeMapper.dtoToEmployee(employeeDto)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,
			@RequestBody @Valid EmployeeDto employeeDto, BindingResult result) {
		if (result.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Employee updatedEmployee = employeeService.update(employeeMapper.dtoToEmployee(employeeDto));
		if (updatedEmployee == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(employeeMapper.employeeSummaryToDto(updatedEmployee));
		}
	}

	@PutMapping(path = "/{id}", params = "positionName")
	public EmployeeDto addPosition(@PathVariable long id, @RequestParam String positionName) {
		try {
			return employeeMapper.employeeSummaryToDto(employeeService.addPosition(id, positionName));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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

}
