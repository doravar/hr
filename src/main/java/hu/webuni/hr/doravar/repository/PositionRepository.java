package hu.webuni.hr.doravar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hu.webuni.hr.doravar.model.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {

	public Optional<Position> findByName (String positionName);

}
