package hu.webuni.hr.doravar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.doravar.model.CompanyType;

public interface CompanyTypeRepository extends JpaRepository<CompanyType, Long> {

	Optional<CompanyType> findByName (String name);
}
