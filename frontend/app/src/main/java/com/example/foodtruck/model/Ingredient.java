package com.example.foodtruck.model;


import com.example.foodtruck.model.user.Operator;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Ingredient implements Serializable {

    private Long id;
    private Ingredient.IngredientName name;
    private int amount;
    private Dish dish;
    private Operator operator;

    public Ingredient(Ingredient.IngredientName name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void subtractAmount(int amount) {
        this.amount -= amount;
    }

    public enum IngredientName {
        MEHL, BUTTER, BROT, OEL, POMMES, SALZ, PFEFFER, MAGGI, TOMATEN, EI, REIS, MICLH, NUDELN, REMOULDAE, BOULETTE,
        BROETCHEN, SALAT, GURKE, KETCHUP, MAYO, SENF, ZUCKER, HONIG, METT, SCHWEINESTEAK, PUTENSTEAK, KARTOFFELN,
        BLUMENKOHL, HOLLANDAISE, KAESE
    }
}
