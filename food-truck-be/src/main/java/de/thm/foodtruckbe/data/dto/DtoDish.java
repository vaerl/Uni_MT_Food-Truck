package de.thm.foodtruckbe.data.dto;

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
    private List<DtoIngredient> ingredients;

    public DtoDish(String name, double basePrice, List<DtoIngredient> dtoIngredients) {
        this.name = name;
        this.basePrice = Math.abs(basePrice);
        this.ingredients = dtoIngredients;
    }

}