package de.thm.foodtruckbe.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.thm.foodtruckbe.entities.Dish.Ingredient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operator {

    private String name;
    private ArrayList<Dish> menu;
    private ArrayList<Location> route;

    private Location currentLocation;

    private HashMap<Location, ArrayList<Dish>> orders;

    public Operator() {
        this.menu = new ArrayList<>();
        this.route = new ArrayList<>();
        this.orders = new HashMap<>();
    }

    public Operator(String name, Location startLocation) {
        this.name = name;
        this.currentLocation = startLocation;
    }

    // TODO check whether removing/adding works correctly - maybe i need to
    // TODO implement the respective equals-methods

    // methods for adding/removing dishes from menu
    public boolean addDishToMenu(Dish dish) {
        return menu.add(dish);
    }

    public boolean removeDishFromMenu(Dish dish) {
        return menu.remove(dish);
    }

    // methods for adding/removing locations from route
    public boolean addLocation(Location location) {
        orders.put(location, new ArrayList<>());
        return route.add(location);
    }

    public boolean addLocations(List<Location> locations) {
        locations.forEach(this::addLocation);
        return true;
    }

    public boolean removeLocation(Location location) {
        orders.remove(location);
        return route.remove(location);
    }

    // methods for ordering
    public boolean addOrderForLocation(Location location, Dish dish) {
        return orders.get(location).add(dish);
    }

    public boolean addAllOrderForLocation(Location location, List<Dish> dish) {
        return orders.get(location).addAll(dish);
    }

    // methods for accessing orders
    public List<Dish> getAllOrders() {
        return orders.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<Dish> getOrdersForLocation(Location location) {
        return orders.get(location);
    }

    public Map<Ingredient, Integer> getShoppingList() {
        EnumMap<Ingredient, Integer> results = new EnumMap<>(Ingredient.class);
        for (ArrayList<Dish> dishes : orders.values()) {
            for (Dish dish : dishes) {
                for (Entry<Ingredient, Integer> entry : dish.getIngredients().entrySet()) {
                    if (results.containsKey(entry.getKey())) {
                        results.put(entry.getKey(), results.get(entry.getKey()) + entry.getValue());
                    } else {
                        results.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return results;
    }

    // methods for changing locations
    public boolean moveToNextLocation() {
        if (orders.isEmpty()) {
            orders.remove(currentLocation);
            this.currentLocation = route.get(0);
            route.remove(0);
            return true;
        }
        return false;
    }

    // TODO check whether doing this here is appropriate!
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Operator) {
            return ((Operator) obj).getName().equals(this.getName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Operator " + name + ": \n-> currently at: " + currentLocation;
    }
}