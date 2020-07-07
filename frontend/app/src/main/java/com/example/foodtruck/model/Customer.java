package com.example.foodtruck.model;

import android.location.Location;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer extends User{

    private Location location;

    public Customer(String name, String password) {
        super(name, password);
    }

    public Customer(String name, String password, Location location) {
        this(name, password);
        this.location = location;
    }
}
