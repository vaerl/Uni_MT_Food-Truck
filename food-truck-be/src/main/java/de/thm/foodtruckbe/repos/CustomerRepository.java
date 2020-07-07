package de.thm.foodtruckbe.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.entities.user.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);
}