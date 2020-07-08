package com.example.foodtruck.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Dish implements Serializable {

    private Long id;
    private String name;
    private double basePrice;
    private double adjustedPrice;
    private double rating;
    private Map<Ingredient, Integer> ingredients;

    public Dish(String name, double basePrice, Map<Ingredient, Integer> ingredients) {
        this.name = name;
        this.basePrice = Math.abs(basePrice);
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        // TODO add ingredients
        return name + ": " + basePrice;
    }

    public enum Ingredient {
        MEHL, BUTTER, BROT, OEL, POMMES, SALZ, PFEFFER, MAGGI, TOMATEN, EI, REIS, MICLH, NUDELN, REMOULDAE, BOULETTE,
        BROETCHEN, SALAT, GURKE, KETCHUP, MAYO, SENF, ZUCKER, HONIG, METT, SCHWEINESTEAK, PUTENSTEAK, KARTOFFELN,
        BLUMENKOHL, HOLLANDAISE, KAESE
    }
}