package com.example.foodtruck.model.order;


import com.example.foodtruck.model.DishWrapper;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Reservation extends Order {

    public Reservation(List<DishWrapper> items) {
        super(items);
    }
}