package de.thm.foodtruckbe.data.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Ingredient;
import de.thm.foodtruckbe.data.entities.user.Operator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoIngredient {

    private Long id;
    private Ingredient.IngredientName name;
    private int amount;
    @JsonIgnore
    private Dish dish;
    @JsonIgnore
    private Operator operator;

    public DtoIngredient(Ingredient.IngredientName name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public void subtractAmount(int amount){
        this.amount -= amount;
    }
}
