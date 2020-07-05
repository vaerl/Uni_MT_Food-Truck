package de.thm.foodtruckbe.entities;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dish {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double price;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    private Operator operator;

    @ElementCollection
    @CollectionTable(name = "ingredient_amount_mapping_dish")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyClass(Ingredient.class)
    @MapKeyColumn(name = "ingredient", nullable = false)
    @Column(name = "amount")
    private Map<Ingredient, Integer> ingredients;

    /**
     * Constructor for {@code Dish}.
     * 
     * @param name
     * @param price
     * @param ingredients
     */
    public Dish(String name, Operator operator, double price, Map<Ingredient, Integer> ingredients) {
        this.name = name;
        this.operator = operator;
        this.price = Math.abs(price);
        this.ingredients = ingredients;
    }

    /**
     * Adds a given rating by calculating its average.
     * 
     * @param rating the given rating
     * @return
     */
    public boolean addRating(int rating) {
        this.rating = (this.rating * rating) / 2;
        return true;
    }

    @Override
    public String toString() {
        // TODO add ingredients
        return name + ": " + price;
    }

    public enum Ingredient {
        FLOUR, BUTTER, BREAD, PORK, OIL, FRIES, SALT, PEPPER, WHEAT, TOMATO, EGGS
    }
}