package de.thm.foodtruckbe.data.dto;

import de.thm.foodtruckbe.data.entities.Ingredient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DtoDish {

    private Long id;
    private String name;
    private double basePrice;
    private double adjustedPrice;
    private double rating;
    private List<Ingredient> ingredients;

    public DtoDish(String name, double basePrice, List<Ingredient> ingredients) {
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