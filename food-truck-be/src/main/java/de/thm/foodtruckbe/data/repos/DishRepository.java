package de.thm.foodtruckbe.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.data.entities.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAll();

}