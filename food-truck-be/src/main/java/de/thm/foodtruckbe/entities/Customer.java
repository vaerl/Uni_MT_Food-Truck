package de.thm.foodtruckbe.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import de.thm.foodtruckbe.entities.order.PreOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Customer {

    private String name;
    private Location location;
    private ArrayList<PreOrder> orders;

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, Location location) {
        this(name);
        this.location = location;
    }

    // methods for adding/removing dishes from the order
    public boolean addDish(PreOrder order) {
        return orders.add(order);
    }

    public boolean removeDish(PreOrder order) {
        // TODO check if this works
        return orders.remove(order);
    }

    public List<Location> getNearestLocations(Operator operator) {
        return operator.getRoute().stream()
                .collect(Collectors.toMap(Function.identity(), e -> e.calculateDistance(this.location).getLength()))
                .entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()).stream()
                .map(Map.Entry::getKey).limit(3).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return name + ": \n orders: " + orders.toString();
    }
}