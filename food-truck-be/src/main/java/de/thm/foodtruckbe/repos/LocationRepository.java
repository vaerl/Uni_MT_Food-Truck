package de.thm.foodtruckbe.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.thm.foodtruckbe.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    List<Location> findAll();

    Optional<Location> findById(Long id);
}