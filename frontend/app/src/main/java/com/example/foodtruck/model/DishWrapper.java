package com.example.foodtruck.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DishWrapper implements Serializable {

    private Long id;
    private Dish dish;

    private int amount;

    public DishWrapper(Dish dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

}
