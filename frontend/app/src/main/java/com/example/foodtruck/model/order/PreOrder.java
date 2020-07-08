package com.example.foodtruck.model.order;

import com.example.foodtruck.model.Dish;

import java.util.Map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PreOrder extends Order {

    public PreOrder(Map<Dish, Integer> items) {
        super(items);
    }
}
