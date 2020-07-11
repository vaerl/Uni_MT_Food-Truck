package de.thm.foodtruckbe.data.repos;

import de.thm.foodtruckbe.data.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAll();

    Optional<Ingredient> findById(Long id);
}
