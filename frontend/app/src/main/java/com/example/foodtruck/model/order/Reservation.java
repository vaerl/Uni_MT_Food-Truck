package com.example.foodtruck.model.order;

import com.example.foodtruck.model.Dish;

import java.util.Map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Reservation extends Order  {

    public Reservation(Map<Dish, Integer> items) {
        super(items);
    }
}