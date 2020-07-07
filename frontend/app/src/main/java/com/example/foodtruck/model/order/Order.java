package com.example.foodtruck.model.order;

import android.os.Parcelable;

import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Location;
import com.example.foodtruck.model.user.Customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Order implements Serializable {

    private Long id;
    private Location location;
    protected Map<Dish, Integer> items;
    protected Customer customer;
    protected double price;
    protected Status status;

    public Order(Customer customer, Map<Dish, Integer> items) {
        this.customer = customer;
        this.items = items;
        this.price = new ArrayList<>(items.entrySet()).stream()
                .map(e -> e.getKey().getPrice() * e.getValue()).reduce(0d, Double::sum);
        this.status = Status.ACCEPTED;
    }

    @Override
    public String toString() {
        return "Order:\n -> Price: " + price + "\n -> Status: " + status + "\n -> Items: "
                + new ArrayList<>(this.items.entrySet()).stream()
                .map(e -> e.getKey().getName() + " " + e.getValue()).reduce("", (a, b) -> a + ", " + b);
    }

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}
