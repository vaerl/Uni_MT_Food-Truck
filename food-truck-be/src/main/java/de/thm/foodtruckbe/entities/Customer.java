package de.thm.foodtruckbe.entities;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

    private String name;
    private ArrayList<Order> orders;

    public Customer(String name) {
        this.name = name;
    }

    // methods for adding/removing dishes from the order
    public boolean addDish(Order order) {
        return orders.add(order);
    }

    public boolean removeDish(Order order) {
        // TODO check if this works
        return orders.remove(order);
    }

    @Override
    public String toString() {
        return name + ": \n orders: " + orders.toString();
    }
}