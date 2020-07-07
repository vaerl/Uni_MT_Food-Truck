package com.example.foodtruck.model.order;

import android.os.Parcel;

import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.user.Customer;

import java.util.Map;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Reservation extends Order  {

    public Reservation(Customer customer, Map<Dish, Integer> items) {
        super(customer, items);
    }
}