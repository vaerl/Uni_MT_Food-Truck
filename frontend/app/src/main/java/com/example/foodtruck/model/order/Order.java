package com.example.foodtruck.model.order;


import com.example.foodtruck.model.DishWrapper;
import com.example.foodtruck.model.Location;
import com.example.foodtruck.model.user.Customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Order implements Serializable {

    private Long id;
    private Location location;
    protected List<DishWrapper> items;
    protected Customer customer;
    protected double price;
    protected Status status;

    public Order(List<DishWrapper> items) {
        this.items = items;
        this.price = items.stream()
                .map(e -> e.getDish().getAdjustedPrice() * e.getAmount()).reduce(0d, Double::sum);
        this.status = Status.ACCEPTED;
    }

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}
