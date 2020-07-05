package de.thm.foodtruckbe.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.entities.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAll();
}