package hu.webuni.hr.doravar.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import hu.webuni.hr.doravar.dto.EmployeeDto;
import hu.webuni.hr.doravar.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	List<EmployeeDto> employeesToDtos(List<Employee> employees);

	List<Employee> dtosToEmployees(List<EmployeeDto> employees);

	@Mapping(target = "companyName", source = "company.name")	// csak a company neve menjen át; getter-setter kell a dto-ba is
	@Mapping(target = "positionName", source = "position.name")
	EmployeeDto employeeToDto(Employee employee);

	@Mapping(target = "company", ignore = true)
	@Mapping(target = "position", ignore = true)
	Employee dtoToEmployee(EmployeeDto employeeDto);

}