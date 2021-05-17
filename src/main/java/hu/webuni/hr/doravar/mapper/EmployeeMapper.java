package hu.webuni.hr.doravar.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	@Mapping(target = "companyName", ignore = true)
	@Mapping(target = "positionName", ignore = true)
	EmployeeDto employeeToDto(Employee employee);

	@Mapping(target = "companyName", source = "company.name")
	@Mapping(target = "positionName", source = "position.name")
	@Named("summary")
	EmployeeDto employeeSummaryToDto(Employee employee);

	@Mapping(target = "company.name", source = "companyName")
	@Mapping(target = "position.name", source = "positionName")
	@Named("summaryToEntity")
	Employee dtoToEmployee(EmployeeDto employeeDto);

	List<EmployeeDto> employeesToDtos(List<Employee> employees);

	@IterableMapping(qualifiedByName = "summary")
	List<EmployeeDto> employeesSummariesToDtos(List<Employee> employees);

	@IterableMapping(qualifiedByName = "summaryToEntity")
	List<Employee> dtosToEmployees(List<EmployeeDto> employees);

}