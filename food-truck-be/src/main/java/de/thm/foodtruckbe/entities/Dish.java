package de.thm.foodtruckbe.entities;

import java.util.EnumMap;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Dish {

    private String name;
    private double price;
    private int servings;

    private EnumMap<Ingredient, Integer> ingredients;

    /**
     * Constructor for {@code Dish}.
     * 
     * @param name
     * @param price
     * @param servings
     * @param ingredients
     */
    public Dish(String name, double price, int servings, EnumMap<Ingredient, Integer> ingredients) {
        this.name = name;
        this.price = Math.abs(price);
        this.servings = Math.abs(servings);
        this.ingredients = ingredients;
    }

    /**
     * Serve the dish. Reduces {@code servings} by one if availabe.
     * 
     * @return success of serving.
     */
    public boolean serve() {
        if (isAvailable()) {
            servings--;
            return true;
        }
        return false;
    }

    /**
     * Returns if more than zero servings are available.
     * 
     * @return
     */
    public boolean isAvailable() {
        return servings > 0;
    }

    @Override
    public String toString() {
        return name + ": " + price + " - " + servings + " available";
    }

    public enum Ingredient {
        FLOUR, BUTTER, BREAD, PORK, OIL, FRIES, SALT, PEPPER, WHEAT, TOMATO, EGGS
    }
}