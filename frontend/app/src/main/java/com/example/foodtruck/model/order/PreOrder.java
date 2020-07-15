package com.example.foodtruck.model.order;

import com.example.foodtruck.model.DishWrapper;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PreOrder extends Order {

    public PreOrder(List<DishWrapper> items) {
        super(items);
    }

}
