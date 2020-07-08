package de.thm.foodtruckbe.data.dto;

import de.thm.foodtruckbe.data.entities.Dish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class DtoDish {

    private Long id;
    private String name;
    private double basePrice;
    private double adjustedPrice;
    private double rating;
    private Map<Dish.Ingredient, Integer> ingredients;

    public DtoDish(String name, double basePrice, Map<Dish.Ingredient, Integer> ingredients) {
        this.name = name;
        this.basePrice = Math.abs(basePrice);
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        // TODO add ingredients
        return name + ": " + basePrice;
    }
}