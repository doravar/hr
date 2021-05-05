package hu.webuni.hr.doravar.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import hu.webuni.hr.doravar.model.Company;
import hu.webuni.hr.doravar.model.PositionDetailsByCompany;

public interface PositionDetailsByCompanyRepository extends JpaRepository<PositionDetailsByCompany, Long> {

	List<PositionDetailsByCompany> findByPositionNameAndCompanyId(String positionName, long companyId);

	@Transactional
	long deleteByCompany(Company company);
}
