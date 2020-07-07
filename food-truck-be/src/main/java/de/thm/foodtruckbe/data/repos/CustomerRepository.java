package de.thm.foodtruckbe.data.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.data.entities.user.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAll();

    Optional<Customer> findById(Long id);
}