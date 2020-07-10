package de.thm.foodtruckbe.data.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.dto.DtoIngredient;
import de.thm.foodtruckbe.data.entities.user.Operator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    @JsonManagedReference(value = "ingredients")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @JsonManagedReference
    private Operator operator;

    public Ingredient(String name, int amount, Dish dish, Operator operator){
        this.name = name;
        this.amount = amount;
        this.dish = dish;
        this.operator = operator;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public void subtractAmount(int amount){
        this.amount -= amount;
    }

    public static Ingredient create(DtoIngredient dtoIngredient, Dish dish, Operator operator){
        return new Ingredient(dtoIngredient.getName(), dtoIngredient.getAmount(), dish, operator);
    }

    public enum IngredientName {
        MEHL, BUTTER, BROT, OEL, POMMES, SALZ, PFEFFER, MAGGI, TOMATEN, EI, REIS, MICLH, NUDELN, REMOULDAE, BOULETTE,
        BROETCHEN, SALAT, GURKE, KETCHUP, MAYO, SENF, ZUCKER, HONIG, METT, SCHWEINESTEAK, PUTENSTEAK, KARTOFFELN,
        BLUMENKOHL, HOLLANDAISE, KAESE
    }
}
