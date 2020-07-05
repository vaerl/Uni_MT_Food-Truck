package de.thm.foodtruckbe.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAll();

}