package de.thm.foodtruckbe.entities.order;

import java.util.Map;
import java.util.stream.Collectors;

import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class Order {

    protected Map<Dish, Integer> items;
    protected Location location;
    protected double price;
    protected Status status;

    public Order(Location location, Map<Dish, Integer> items) {
        this.items = items;
        this.location = location;
        this.price = items.entrySet().stream().collect(Collectors.toList()).stream()
                .map(e -> e.getKey().getPrice() * e.getValue()).reduce(0d, (a, b) -> a + b);
        this.status = Status.ACCEPTED;
    }

    /**
     * Adds an item to this order and raises its price accordingly. Only accepts
     * positive integers.
     * 
     * @param dish
     * @param amount
     * @return
     */
    public boolean addItem(Dish dish, int amount) {
        if (amount <= 0) {
            return false;
        }
        // check if item is already present -> update amount
        if (items.containsKey(dish)) {
            items.replace(dish, items.get(dish) + amount);
        } else {
            items.put(dish, amount);
        }
        price += dish.getPrice() * amount;
        return true;
    }

    /**
     * Removes the given item.
     * 
     * @param dish
     */
    public boolean removeItem(Dish dish) {
        price -= dish.getPrice();
        return items.remove(dish) != null;
    }

    @Override
    public String toString() {
        return "Order for " + location + ":\n -> Price: " + price + "\n -> Status: " + status + "\n -> Items: "
                + this.items.entrySet().stream().collect(Collectors.toList()).stream()
                        .map(e -> e.getKey().getName() + " " + e.getValue()).reduce("", (a, b) -> a + ", " + b);
    }

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}