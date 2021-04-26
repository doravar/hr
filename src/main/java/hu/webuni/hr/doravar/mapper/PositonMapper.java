package hu.webuni.hr.doravar.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import hu.webuni.hr.doravar.dto.PositionDto;
import hu.webuni.hr.doravar.model.Position;

@Mapper(componentModel = "spring")
public interface PositonMapper {

	List<PositionDto> positionsToDtos(List<Position> positions);

	List<Position> dtosToPositions(List<PositionDto> positions);
	
	PositionDto positionToDto(Position position);

	Position dtoToPosition(PositionDto positionDto);

}