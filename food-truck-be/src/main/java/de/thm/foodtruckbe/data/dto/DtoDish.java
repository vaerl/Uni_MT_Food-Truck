package de.thm.foodtruckbe.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private List<DtoIngredient> dtoIngredients;

    public DtoDish(String name, double basePrice, List<DtoIngredient> dtoIngredients) {
        this.name = name;
        this.basePrice = Math.abs(basePrice);
        this.dtoIngredients = dtoIngredients;
    }

}