package hu.webuni.hr.doravar.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.doravar.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, PagingAndSortingRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

	Page<Employee> findAll(Pageable pageable);

	List<Employee> findAllByOrderByIdAsc();

	Optional<Employee> findByUsername(String username);
	
	List<Employee> findBySalaryGreaterThan(Integer minSalary);

	List<Employee> findByPositionName(String positionName); // uaz mint @Query("SELECT DISTINCT e FROM Employee e JOIN
	// e.position p WHERE p.name = :jobTitle")

	List<Employee> findByNameStartingWithIgnoreCase(String fragment);

	List<Employee> findByEntryDateBetween(LocalDate start, LocalDate end);

	@Query("SELECT AVG(e.salary) AS avgsalary, e.position.name AS jobtitle FROM Employee AS e WHERE e.company.id = :companyId GROUP BY e.position.name ORDER BY AVG(e.salary) DESC")
	List<Object[]> countAvgSalaryByJobtitle(Long companyId);
// List-ban Object tömbök, bennük avgsalary és position -> comapnyrepo-ban lévő megoldás inkább egy erre létrehozott interface-t használ

	@Modifying
	@Transactional
	@Query("UPDATE Employee e " // UPDATE és WHERE nem működik együtt jpql-ben, ezért kell így
			+ "SET e.salary = :minSalary " + "WHERE e.id IN (" + "SELECT e2.id " + "FROM Employee e2 "
			+ "WHERE e2.position.name=:positionName " + "AND e2.salary< :minSalary " + "AND e2.company.id=:companyId)")
	int updateSalaries(String positionName, int minSalary, long companyId); // int visszatérési: hány sor módosult
}
