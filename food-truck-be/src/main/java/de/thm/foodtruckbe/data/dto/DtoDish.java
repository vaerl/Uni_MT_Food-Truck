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
    private double price;
    private double rating;
    private Map<Dish.Ingredient, Integer> ingredients;

    public DtoDish(String name, double price, Map<Dish.Ingredient, Integer> ingredients) {
        this.name = name;
        this.price = Math.abs(price);
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        // TODO add ingredients
        return name + ": " + price;
    }
}