package hu.webuni.hr.doravar.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.doravar.dto.HolidayDto;
import hu.webuni.hr.doravar.model.Holiday;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HolidayMapper {

	@Mapping(target = "employeeId", source = "employee.id")
	@Mapping(target = "approverId", source = "approver.id")
	
	HolidayDto holidayToDto(Holiday holiday);

	@Mapping(target = "employee.id", source = "employeeId")
	@Mapping(target = "approver.id", source = "approverId")
	Holiday dtoToHoliday(HolidayDto holidayDto);

	List<HolidayDto> holidaysToDtos(List<Holiday> holidays);

	List<Holiday> dtosToHolidays(List<HolidayDto> holidayDtos);
}
