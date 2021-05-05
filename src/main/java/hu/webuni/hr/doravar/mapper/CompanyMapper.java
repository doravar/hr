package hu.webuni.hr.doravar.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.doravar.dto.CompanyDto;
import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Employee;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

	// két félét csinálunk lekérésekből: employee listával és anélkül
	CompanyDto companyToDto(Company company);

	@Mapping(target = "employees", ignore = true)		// employees nélkül visszaadott companydto
	@Named("summary")
	CompanyDto companySummaryToDto(Company company);

	List<CompanyDto> companiesToDtos(List<Company> companies);

	@IterableMapping(qualifiedByName = "summary")		// "summary" nevű mappelés alapján készítse a listát
	List<CompanyDto> companySummariesToDtos(List<Company> companies);

	Company dtoToCompany(CompanyDto companyDto);
	
	@Mapping(target = "companyName", source = "company.name")	// ugyanúgy mint empmapperben
	@Mapping(target = "positionName", source = "position.name")
	EmployeeDto employeeToDto(Employee employee);
}