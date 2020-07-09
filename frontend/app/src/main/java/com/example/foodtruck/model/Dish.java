package com.example.foodtruck.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Dish implements Serializable {

    private Long id;
    private String name;
    private double basePrice;
    private double adjustedPrice;
    private double rating;
    private List<Ingredient> ingredients;

    public Dish(String name, double basePrice, List<Ingredient> ingredients) {
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