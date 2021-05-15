package hu.webuni.hr.doravar.web;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import hu.webuni.hr.doravar.mapper.CompanyMapper;
import hu.webuni.hr.doravar.mapper.EmployeeMapper;
import hu.webuni.hr.doravar.model.AverageSalaryByPosition;
import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.CompanyType;
import hu.webuni.hr.doravar.repository.CompanyRepository;
import hu.webuni.hr.doravar.repository.CompanyTypeRepository;
import hu.webuni.hr.doravar.service.CompanyService;

@RestController
@RequestMapping("/api/companies")

public class CompanyController {

	@Autowired
	CompanyService companyService;

	@Autowired
	CompanyMapper companyMapper;

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	CompanyRepository companyRepository;

	@GetMapping
	public List<CompanyDto> getCompanys(@RequestParam(required = false) Boolean full) {
		return full == null || full == false ? companyMapper.companySummariesToDtos(companyService.findAll())
				// employees nélküli company lista,mappereléssel érjük el hogy ne tegye bele
				: companyMapper.companiesToDtos(companyService.findAllWithEmployees());

	}

	@GetMapping("/{id}")
	public CompanyDto getById(@PathVariable long id, @RequestParam(required = false) Boolean full) {
		try {
			return full == null || full == false ? companyMapper.companySummaryToDto(companyService.findById(id).get())
					: companyMapper.companyToDto(companyService.findByIdWithEmployees(id).get());

		} catch (NoSuchElementException | NullPointerException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(params = "minSalary")
	public List<CompanyDto> getByEmployeesWithMinSalary(@RequestParam int minSalary) {
		return companyMapper.companiesToDtos(companyService.listCompaniesWhereEmployeesGetMinSalary(minSalary));
	}

	@GetMapping(params = "minEmployees")
	public List<CompanyDto> getByNumberOfEmployeesExceeds(@RequestParam int minEmployees) {
		return companyMapper.companiesToDtos(companyService.listCompaniesWhereNumberOfEmployeesExceeds(minEmployees));
	}

	@GetMapping("/{id}/salaryStats")
	public List<AverageSalaryByPosition> getSalaryStatsById(@PathVariable long id) {
		return companyRepository.findAverageSalariesByPosition(id);
	}

	@PostMapping
	public CompanyDto createCompany(@RequestBody CompanyDto companyDto) {
		Company company = companyService.save(companyMapper.dtoToCompany(companyDto)); // save update-eli is, tehát ha
																						// volt ilyen id, felülírja!
		return companyMapper.companyToDto(company); /*
													 * a return az új objektumot küldi vissza miután a felvétele
													 * megtörtént; két konverzió, hogy a közben belekerült adatok is
													 * szerepeljenek a visszaadott dto-ban
													 */
	}

	@PostMapping("/{id}/addNewEmployee")
	public CompanyDto addNewEmployee(@PathVariable long id, @RequestBody EmployeeDto employeeDto) {
		try {
			return companyMapper
					.companyToDto(companyService.addEmployee(id, employeeMapper.dtoToEmployee(employeeDto)));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id, @RequestBody CompanyDto companyDto) {
		companyDto.setId(id); /* itt cseréljük a dto-ban érkező id-t a hívásban lévőre (ha eltérő lenne) */
		Company updatedCompany = companyService.update(companyMapper.dtoToCompany(companyDto));
		if (updatedCompany == null) { // nem létező id-nál service null-t ad vissza
			return ResponseEntity.notFound().build(); /* ha nincs id, 404-es hiba */
		}
		return ResponseEntity.ok(companyMapper.companyToDto(updatedCompany));
	}

	@PutMapping("/{id}/replaceEmployees")
	public CompanyDto replaceEmployees(@PathVariable long id, @RequestBody List<EmployeeDto> employees) {
		try {
			return companyMapper
					.companyToDto(companyService.replaceEmployees(id, employeeMapper.dtosToEmployees(employees)));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/{id}", params = "companyTypeName")
	public CompanyDto addCompanyType(@PathVariable long id, @RequestParam String companyTypeName) {
		try {
			return companyMapper.companySummaryToDto(companyService.addCompanyType(id, companyTypeName));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable long id) {
		try {
			companyService.deleteById(id);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}/employees/{employeeId}")
	public CompanyDto deleteEmployee(@PathVariable long id, @PathVariable long employeeId) { // csak a cégből vesszük
																								// ki, nem töröljük az
																								// employeet
		try {
			return companyMapper.companyToDto(companyService.deleteEmployee(id, employeeId));
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

}
