package com.example.foodtruck.model.order;

import com.example.foodtruck.model.DishWrapper;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class PreOrder extends Order {

    public PreOrder(List<DishWrapper> items) {
        super(items);
    }

}
