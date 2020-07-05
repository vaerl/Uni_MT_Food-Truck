package de.thm.foodtruckbe.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findAll();
}