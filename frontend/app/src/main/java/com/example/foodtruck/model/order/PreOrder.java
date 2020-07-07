package com.example.foodtruck.model.order;

import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Location;
import com.example.foodtruck.model.user.Customer;

import java.util.Map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PreOrder extends Order {

    public PreOrder(Customer customer, Map<Dish, Integer> items) {
        super(customer, items);
    }

}
