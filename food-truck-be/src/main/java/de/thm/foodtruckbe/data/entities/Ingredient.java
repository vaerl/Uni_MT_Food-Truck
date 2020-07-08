package de.thm.foodtruckbe.data.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    private IngredientName name;
    private int amount;

    @ManyToOne
    @JoinColumn(name = "dish_id", nullable = false)
    @JsonManagedReference
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @JsonManagedReference
    private Operator operator;

    public Ingredient(IngredientName name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public void subtractAmount(int amount){
        this.amount -= amount;
    }

    public enum IngredientName {
        MEHL, BUTTER, BROT, OEL, POMMES, SALZ, PFEFFER, MAGGI, TOMATEN, EI, REIS, MICLH, NUDELN, REMOULDAE, BOULETTE,
        BROETCHEN, SALAT, GURKE, KETCHUP, MAYO, SENF, ZUCKER, HONIG, METT, SCHWEINESTEAK, PUTENSTEAK, KARTOFFELN,
        BLUMENKOHL, HOLLANDAISE, KAESE
    }
}
