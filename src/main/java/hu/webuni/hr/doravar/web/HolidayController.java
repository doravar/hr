package hu.webuni.hr.doravar.web;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import hu.webuni.hr.doravar.dto.HolidayDto;
import hu.webuni.hr.doravar.dto.HolidayRequestFilterDto;
import hu.webuni.hr.doravar.mapper.HolidayMapper;
import hu.webuni.hr.doravar.model.Holiday;
import hu.webuni.hr.doravar.repository.HolidayRepository;
import hu.webuni.hr.doravar.service.HolidayService;

@RestController
@RequestMapping("/api/holidays")

public class HolidayController {

	@Autowired
	HolidayService holidayService;

	@Autowired
	HolidayMapper holidayMapper;

	@Autowired
	HolidayRepository holidayRepository;

	@GetMapping
	public List<HolidayDto> getHolidays() {
		return holidayMapper.holidaysToDtos(holidayService.findAll());

	}

	@GetMapping("/{id}")
	public HolidayDto getById(@PathVariable long id) {
		try {
			return holidayMapper.holidayToDto(holidayRepository.findById(id).get());
		} catch (NoSuchElementException | NullPointerException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/pageable")
	public List<HolidayDto> getHolidays(Pageable pageable) {
		Page<Holiday> pageOfHolidays = holidayRepository.findAll(pageable);
		return holidayMapper.holidaysToDtos(pageOfHolidays.getContent());
	}

	@PostMapping
	@PreAuthorize("#holidayDto.employeeId == authentication.principal.employee.id") // authentication.principal: HrUser
																					// employee-ra utal
	public HolidayDto createHoliday(@RequestBody @Valid HolidayDto holidayDto) {
		Holiday holiday = holidayService.save(holidayMapper.dtoToHoliday(holidayDto));
		return holidayMapper.holidayToDto(holiday);
	}

	@PutMapping("/{id}")
	@PreAuthorize("#holidayDto.employeeId == authentication.principal.employee.id")
	public ResponseEntity<HolidayDto> modifyHoliday(@PathVariable long id, @RequestBody @Valid HolidayDto holidayDto) {
		try {
			holidayDto.setId(id);
			Holiday updatedHoliday = holidayService.update(holidayMapper.dtoToHoliday(holidayDto));
			if (updatedHoliday == null) {
				return ResponseEntity.notFound().build();
			}
			return ResponseEntity.ok(holidayMapper.holidayToDto(updatedHoliday));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping(path = "/approval/{id}", params = "stateOfHolidayRequest")
	public ResponseEntity<HolidayDto> approveHoliday(@PathVariable long id,
			@RequestParam String stateOfHolidayRequest) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		try {
			if (!holidayRepository.findById(id).get().getApprover().getUsername().equals(username)) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
			}
			return ResponseEntity.ok(holidayMapper.holidayToDto(holidayService.approve(id, stateOfHolidayRequest)));
		} catch (NoSuchElementException e) {
			return ResponseEntity.notFound().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping(path = "/deleteholiday")
	public void deleteHoliday(@RequestParam(name = "holidayId") long id) {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = user.getUsername();
		try {
			if (!holidayRepository.findById(id).get().getEmployee().getUsername().equals(username)) {
				throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
			}
			holidayService.deleteById(id);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST); // ha már el lett bírálva
		}

	}

	@PostMapping("/getByExample")
	public List<HolidayDto> findByExample(@RequestBody HolidayRequestFilterDto example, Pageable pageable) {
		Page<Holiday> page = holidayService.findHolidaysByExample(example, pageable);
		return holidayMapper.holidaysToDtos(page.getContent());
	}

}
