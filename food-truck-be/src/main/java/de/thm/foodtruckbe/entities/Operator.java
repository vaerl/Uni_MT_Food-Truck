package de.thm.foodtruckbe.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.thm.foodtruckbe.entities.Dish.Ingredient;
import de.thm.foodtruckbe.entities.Location.Status;
import de.thm.foodtruckbe.entities.order.Order;
import de.thm.foodtruckbe.entities.order.PreOrder;
import de.thm.foodtruckbe.entities.order.Reservation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operator {

    private String name;
    private ArrayList<Dish> menu;
    private ArrayList<Dish> preorderMenu;
    private ArrayList<Location> route;

    private Location currentLocation;
    private Map<Ingredient, Integer> stock;

    private HashMap<Location, ArrayList<PreOrder>> preorders;
    private HashMap<Location, ArrayList<Reservation>> reservations;

    public Operator() {
        this.menu = new ArrayList<>();
        this.preorderMenu = new ArrayList<>();
        this.route = new ArrayList<>();
        this.preorders = new HashMap<>();
        this.reservations = new HashMap<>();
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
        preorders.put(location, new ArrayList<>());
        return route.add(location);
    }

    public boolean addLocations(List<Location> locations) {
        locations.forEach(this::addLocation);
        return true;
    }

    public boolean removeLocation(Location location) {
        preorders.remove(location);
        return route.remove(location);
    }

    // preorders
    public boolean addPreOrderForLocation(Location location, PreOrder preOrder) {
        if (isBeforeNextDay(location)) {
            return false;
        }
        return preorders.get(location).add(preOrder);
    }

    public boolean addAllPreOrdersForLocation(Location location, List<PreOrder> preOrders) {
        if (isBeforeNextDay(location)) {
            return false;
        }
        return this.preorders.get(location).addAll(preOrders);
    }

    public boolean removePreOrderFromLocation(Location location, PreOrder preOrder) {
        if (isBeforeNextDay(location)) {
            return false;
        }
        return preorders.get(location).remove(preOrder);
    }

    // get preorders
    public List<PreOrder> getAllOrders() {
        return preorders.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public List<PreOrder> getOrdersForLocation(Location location) {
        return preorders.get(location);
    }

    // reservations
    public boolean addReservationForLocation(Location location, Reservation reservation) {
        if (!isPossible(reservation)) {
            return false;
        }
        return this.reservations.get(location).add(reservation);
    }

    public boolean addAllReservationsForLocation(Location location, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            if (!isPossible(reservation)) {
                return false;
            }
        }
        return this.reservations.get(location).addAll(reservations);
    }

    // TODO remove reservation
    public boolean removeReservationFromLocation(Location location, Reservation reservation) {
        // remove reservation
        if (this.reservations.get(location).remove(reservation)) {
            // update stock
            addToStock(reservation.getItems());
            return true;
        } else {
            return false;
        }
    }

    // TODO get reservations

    // shopping
    public Map<Ingredient, Integer> getShoppingList() {
        // TODO include only preorders - and allow setting extra portions
        EnumMap<Ingredient, Integer> results = new EnumMap<>(Ingredient.class);
        // TODO fix using stream
        for (ArrayList<Dish> dishes : preorders.values()) {
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

    public void goShopping(Map<Ingredient, Integer> ingredients) {
        // get available ingredients
        stock = Market.buyIngredients(ingredients);
        // check possible orders and adjust each status
        preorders.values().stream().forEach(list -> {
            list.stream().forEach(order -> {
                if (isPossible(order)) {
                    order.setStatus(Order.Status.CONFIRMED);
                } else {
                    order.setStatus(Order.Status.NOT_POSSIBLE);
                }
            });
        });
        // update reservationMenu
        // TODO
    }

    // check orders

    private boolean isPossible(Order order) {
        for (Map.Entry<Dish, Integer> dishEntry : order.getItems().entrySet()) {
            for (Map.Entry<Ingredient, Integer> ingredientEntry : dishEntry.getKey().getIngredients().entrySet()) {
                if (dishEntry.getValue() * ingredientEntry.getValue() > stock.get(ingredientEntry.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isBeforeNextDay(Location location) {
        return LocalDateTime.now()
                .isBefore(LocalDateTime.of(location.getArrival().toLocalDate().plusDays(1), LocalTime.of(7, 0, 0)));
    }

    // interact with stock
    public boolean addToStock(Ingredient ingredient, int amount) {
        if(){
            return true;
        }
        return false;
    }

    public boolean addToStock(Map<Dish, Integer> items) {
        for (Map.Entry<Dish, Integer> dishEntry : items.entrySet()) {
            for (Map.Entry<Ingredient, Integer> ingredientEntry : dishEntry.getKey().getIngredients().entrySet()) {
                return addToStock(ingredientEntry.getKey() , dishEntry.getValue() * ingredientEntry.getValue())
            }
        }
        return false;
    }

    public boolean removeFromStock(Map<Dish, Integer> items) {
        return false;
    }

    // methods for changing locations
    public boolean moveToNextLocation() {
        if (preorders.isEmpty()) {
            preorders.remove(currentLocation);
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