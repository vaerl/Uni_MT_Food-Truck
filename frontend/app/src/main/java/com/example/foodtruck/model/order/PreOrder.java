package com.example.foodtruck.model.order;

import android.os.Parcel;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
