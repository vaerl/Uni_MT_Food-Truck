package de.thm.foodtruckbe.data.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.data.entities.user.Operator;

public interface OperatorRepository extends JpaRepository<Operator, Long> {

    List<Operator> findByName(String name);

    Optional<Operator> findById(Long id);
}