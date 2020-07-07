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
    private double price;
    private double rating;
    private Map<Ingredient, Integer> ingredients;

    public Dish(String name, double price, Map<Ingredient, Integer> ingredients) {
        this.name = name;
        this.price = Math.abs(price);
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        // TODO add ingredients
        return name + ": " + price;
    }

    public enum Ingredient {
        MEHL, BUTTER, BROT, OEL, POMMES, SALZ, PFEFFER, MAGGI, TOMATEN, EI, REIS, MICLH, NUDELN, REMOULDAE, BOULETTE,
        BROETCHEN, SALAT, GURKE, KETCHUP, MAYO, SENF, ZUCKER, HONIG, METT, SCHWEINESTEAK, PUTENSTEAK, KARTOFFELN,
        BLUMENKOHL, HOLLANDAISE, KAESE
    }
}