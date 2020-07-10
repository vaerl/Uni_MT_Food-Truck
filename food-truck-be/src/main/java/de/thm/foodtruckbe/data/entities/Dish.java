package de.thm.foodtruckbe.data.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.dto.DtoDish;
import de.thm.foodtruckbe.data.dto.DtoIngredient;
import de.thm.foodtruckbe.data.entities.user.Operator;
import de.thm.foodtruckbe.data.repos.DishRepository;
import de.thm.foodtruckbe.data.repos.IngredientRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Dish {

    @Id
    @GeneratedValue
    @Column(name = "dish_id")
    private Long id;

    private String name;
    private double basePrice;
    private double adjustedPrice;
    private double rating;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @JsonManagedReference
    private Operator operator;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish")
    @JsonBackReference(value = "dish-ingredient")
    private List<Ingredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish")
    @JsonBackReference(value = "dish-dishwrapper")
    private List<DishWrapper> dishWrappers;

    /**
     * Constructor for {@code Dish}.
     *
     * @param name
     * @param basePrice
     * @param ingredients
     */
    public Dish(String name, Operator operator, double basePrice, List<Ingredient> ingredients) {
        this.name = name;
        this.operator = operator;
        this.basePrice = Math.abs(basePrice);
        this.adjustedPrice = basePrice;
        this.ingredients = ingredients;
    }

    /**
     * Adds a given rating by calculating its average.
     *
     * @param rating the given rating
     * @return
     */
    public boolean addRating(int rating) {
        if (this.rating == 0) {
            this.rating = 0;
        } else {
            this.rating = (this.rating * rating) / 2;
        }
        return true;
    }

    public static Dish create(DtoDish dtoDish, Operator operator) {
        Dish dish = new Dish(dtoDish.getName(), operator, dtoDish.getBasePrice(), null);
        List<Ingredient> ingredients = new ArrayList<>();
        if (dtoDish.getIngredients() != null) {
            for (DtoIngredient dtoIngredient : dtoDish.getIngredients()) {
                Ingredient i = Ingredient.create(dtoIngredient, dish, operator);
                ingredients.add(i);
            }
        }
        dish.setIngredients(ingredients);
        return dish;
    }

    public Dish merge(Dish dish) {
        this.adjustedPrice = dish.adjustedPrice;
        this.basePrice = this.basePrice;
        this.ingredients = dish.ingredients;
        this.name = dish.name;
        this.rating = rating;
        return this;
    }
}