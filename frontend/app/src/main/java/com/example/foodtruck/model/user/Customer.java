package com.example.foodtruck.model.user;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.foodtruck.model.Location;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer extends User implements Serializable {

    private Location location;

    public Customer(String name, String password) {
        super(name, password);
    }

    public Customer(String name, String password, Location location) {
        this(name, password);
        this.location = location;
    }
}
