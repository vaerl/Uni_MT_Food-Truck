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
    private int rating;

    public Order(Dish dish, int amount) {
        this.dish = dish;
        if (Math.abs(amount) > dish.getServings()) {
            // TODO defining a special exception might be better Rest-Error-Handling
            throw new IllegalArgumentException("The amount " + amount + "is too high for dish " + dish.toString());
        }
        this.amount = Math.abs(amount);
        this.price = dish.getPrice() * this.amount;
    }

    public void setRating(int rating) {
        if (rating > 5) {
            rating = 5;
        }
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Order of " + dish.getName() + ": " + amount + " times for " + price + "€";
    }

    private enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}