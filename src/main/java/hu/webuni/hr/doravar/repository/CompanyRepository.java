package hu.webuni.hr.doravar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.Employee;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	@Query("SELECT DISTINCT c FROM Company c JOIN c.employees e WHERE e.salary > ?1")		// distinct: ha nincs, akkor ahol több alkalmazottra is igaz, többször adja vissza az adott companyt
	List<Company> findCompaniesWhereEmployeesGetMinSalary (int salary);
	
	@Query("SELECT c FROM Company c WHERE SIZE(c.employees)> ?1")
	List<Company> findCompaniesWhereNumberOfEmployeesExceeds (int limit);
	

}
