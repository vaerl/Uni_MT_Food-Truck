package de.thm.foodtruckbe.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Order {

    private Dish dish;
    private int amount;
    private double price;
    private Status status;

    public Order(Dish dish, int amount) {
        this.dish = dish;
        if (Math.abs(amount) > dish.getServings()) {
            // TODO check if defining own exception works better with Rest-Error-Handling
            throw new IllegalArgumentException("The amount " + amount + "is too high for dish " + dish.toString());
        }
        this.amount = Math.abs(amount);
        this.price = dish.getPrice() * this.amount;
    }

    @Override
    public String toString() {
        return "[" + dish + "]: " + amount + " times for " + price + "€";
    }

    private enum Status {
        ACCEPTED, NOT_POSSIBLE, DONE
    }
}