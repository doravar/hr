package hu.webuni.hr.doravar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.doravar.model.AverageSalaryByPosition;
import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Employee;
import hu.webuni.hr.doravar.model.Position;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	public Optional<Company> findByName(String companyName);

	@EntityGraph("Company.full")
	public Optional<Company> findWithEmployeesById(long id);

	@EntityGraph("Company.full")
	// distinct: ha nincs, akkor ahol több alkalmazottra is igaz, többször adja
	// vissza az adott companyt
	@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary >:salary")
	public List<Company> findCompaniesWhereEmployeesGetMinSalary(int salary);

	@EntityGraph("Company.full")
	@Query("SELECT c FROM Company c WHERE SIZE(c.employees)> :limit")
	public List<Company> findCompaniesWhereNumberOfEmployeesExceeds(int limit);

// ugyanaz mint amit emp.repoban is megcsináltam countAvgSalaryByJobtitle néven, de saját AverageSalaryByPosition interface-el
	@EntityGraph("Company.full")
	@Query("SELECT e.position.name AS position, avg(e.salary) AS averageSalary FROM Company c JOIN c.employees e WHERE c.id=:companyId GROUP BY e.position.name ORDER BY avg(e.salary) DESC")
	public List<AverageSalaryByPosition> findAverageSalariesByPosition(long companyId);

//	@Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.employees")		// ha nem EntityGraph-al csináljuk a fetch-elést
	@EntityGraph("Company.full") // named entity graph, ilyenkor kell az entitásban is jelezni
	@Query("SELECT c FROM Company c")
	public List<Company> findAllWithEmployees();

	@EntityGraph("Company.full")
	@Query("SELECT c FROM Company c WHERE c.id=:companyId")
	public Company findByIdWithEmployees(Long companyId);

	@Query("SELECT e FROM Company c JOIN c.employees e WHERE c.id=:companyId")
	public List<Employee> findEmployees(Long companyId);

}
