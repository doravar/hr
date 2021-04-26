package hu.webuni.hr.doravar.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long> {

	Page<Employee> findAll(Pageable pageable);

	List<Employee> findAll();

	List<Employee> findBySalaryGreaterThan(Integer minSalary);

	@Query("SELECT DISTINCT e FROM Employee e JOIN e.position p WHERE p.name = ?1")
	List<Employee> findByJobTitle(String jobTitle);

	List<Employee> findByNameStartingWithIgnoreCase(String fragment);

	List<Employee> findByEntryDateBetween(LocalDate start, LocalDate end);

	@Query("SELECT AVG(e.salary) AS avgsalary, e.position.name AS jobtitle FROM Employee AS e WHERE e.company.id = ?1 GROUP BY e.position.name ORDER BY AVG(e.salary) DESC")
	List<Object[]> countAvgSalaryByJobtitle(Long companyId);

}
