package de.thm.foodtruckbe.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.webjars.NotFoundException;

import de.thm.foodtruckbe.entities.Dish.Ingredient;
import de.thm.foodtruckbe.entities.order.Order;
import de.thm.foodtruckbe.entities.order.PreOrder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Operator {

    @Id
    @GeneratedValue
    @Column(name = "operator_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference
    private List<Dish> preOrderMenu;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference
    private List<Dish> reservationMenu;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference
    private List<Location> route;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @JsonIgnore
    private Location currentLocation;

    @ElementCollection
    @CollectionTable(name = "ingredient_amount_mapping_stock")
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyClass(Ingredient.class)
    @MapKeyColumn(name = "ingredient", nullable = false)
    @Column(name = "amount")
    private Map<Ingredient, Integer> stock;

    public Operator() {
        this.preOrderMenu = new ArrayList<>();
        this.reservationMenu = new ArrayList<>();
        this.route = new ArrayList<>();
    }

    /**
     * Constructor for inital use. Sets the {@code Market} as the operators initial
     * location with its start at 7AM and a duration of 30 minutes.
     * 
     * @param name
     */
    public Operator(final String name) {
        this.name = name;
        double[] coordinates = Market.getCoordinates();
        this.currentLocation = new Location("Market", this, coordinates[0], coordinates[1],
                LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0, 0)), Duration.ofMinutes(30));
    }

    // TODO check whether removing/adding works correctly - maybe i need to
    // TODO implement the respective equals-methods

    // methods for adding/removing dishes from menu
    public boolean addDishToMenu(final Dish dish) {
        if (isPossible(dish)) {
            reservationMenu.add(dish);
        }
        return preOrderMenu.add(dish);
    }

    public boolean removeDishFromMenu(final Dish dish) {
        return preOrderMenu.remove(dish) && reservationMenu.remove(dish);
    }

    public Dish getDishFromMenu(Dish dish) {
        for (Dish d : preOrderMenu) {
            if (d.equals(dish)) {
                return d;
            }
        }
        throw new NotFoundException("Dish " + dish.getName() + "does not belong to this operator.");
    }

    // locations
    // methods for adding/removing locations from route
    public boolean addLocation(final Location location) {
        return route.add(location);
    }

    public boolean addLocations(final List<Location> locations) {
        for (Location location : locations) {
            if (addLocation(location)) {
                return false;
            }
        }
        return true;
    }

    public boolean removeLocation(final Location location) {
        return route.remove(location);
    }

    public boolean removeLocations(final List<Location> locations) {
        for (Location location : locations) {
            if (removeLocation(location)) {
                return false;
            }
        }
        return true;
    }

    // get location
    public Location getLocation(Location location) {
        for (Location l : route) {
            if (l.equals(location)) {
                return l;
            }
        }
        throw new NotFoundException("Location " + location.getName() + "does not belong to this operator.");
    }

    // methods for changing locations
    public boolean moveToNextLocation() {
        if (currentLocation.getPreOrders().isEmpty() && currentLocation.getReservations().isEmpty()) {
            this.currentLocation = route.get(0);
            route.remove(0);
            return true;
        }
        return false;
    }

    // TODO get all order-types

    // TODO implement somwthing for easy adjustment of the shopping-list
    // shopping
    public Map<Ingredient, Integer> getShoppingList() {
        final EnumMap<Ingredient, Integer> results = new EnumMap<>(Ingredient.class);
        for (Location location : route) {
            for (PreOrder preOrder : location.getPreOrders()) {
                for (final Map.Entry<Dish, Integer> dishEntry : preOrder.getItems().entrySet()) {
                    for (final Map.Entry<Ingredient, Integer> ingredientEntry : dishEntry.getKey().getIngredients()
                            .entrySet()) {
                        if (results.containsKey(ingredientEntry.getKey())) {
                            results.put(ingredientEntry.getKey(), results.get(ingredientEntry.getKey())
                                    + (dishEntry.getValue() * ingredientEntry.getValue()));
                        } else {
                            results.put(ingredientEntry.getKey(), dishEntry.getValue() * ingredientEntry.getValue());
                        }
                    }

                }
            }
        }
        return results;
    }

    public void goShopping(final Map<Ingredient, Integer> ingredients) {
        // get available ingredients
        stock = Market.buyIngredients(ingredients);
        // check possible orders and adjust each status
        route.stream().forEach(location -> location.getPreOrders().forEach(preOrder -> {
            if (isPossible(preOrder)) {
                // remove items from stock and set status
                removeFromStock(preOrder.getItems());
                preOrder.setStatus(Order.Status.CONFIRMED);
            } else {
                preOrder.setStatus(Order.Status.NOT_POSSIBLE);
            }
        }));
        // update reservationsMenu
        reservationMenu.clear();
        preOrderMenu.forEach(dish -> {
            if (isPossible(dish)) {
                reservationMenu.add(dish);
            }
        });
    }

    private boolean isPossible(Order order) {
        for (final Map.Entry<Dish, Integer> dishEntry : order.getItems().entrySet()) {
            for (final Map.Entry<Ingredient, Integer> ingredientEntry : dishEntry.getKey().getIngredients()
                    .entrySet()) {
                if (dishEntry.getValue() * ingredientEntry.getValue() > stock.get(ingredientEntry.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPossible(final Dish dish) {
        for (final Map.Entry<Ingredient, Integer> ingredient : dish.getIngredients().entrySet()) {
            if (ingredient.getValue() > stock.get(ingredient.getKey())) {
                return false;
            }
        }
        return true;
    }

    // interact with stock
    public boolean addToStock(final Ingredient ingredient, final int amount) {
        if (stock.containsKey(ingredient)) {
            stock.replace(ingredient, stock.get(ingredient) + amount);
        } else {
            stock.put(ingredient, amount);
        }
        return true;
    }

    public boolean addToStock(final Map<Dish, Integer> items) {
        for (final Map.Entry<Dish, Integer> dishEntry : items.entrySet()) {
            for (final Map.Entry<Ingredient, Integer> ingredientEntry : dishEntry.getKey().getIngredients()
                    .entrySet()) {
                if (!addToStock(ingredientEntry.getKey(), dishEntry.getValue() * ingredientEntry.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean removeFromStock(final Ingredient ingredient, final int amount) {
        if (stock.containsKey(ingredient)) {
            stock.replace(ingredient, stock.get(ingredient) - amount);
        } else {
            return false;
        }
        return true;
    }

    public boolean removeFromStock(final Map<Dish, Integer> items) {
        for (final Map.Entry<Dish, Integer> dishEntry : items.entrySet()) {
            for (final Map.Entry<Ingredient, Integer> ingredientEntry : dishEntry.getKey().getIngredients()
                    .entrySet()) {
                if (!removeFromStock(ingredientEntry.getKey(), dishEntry.getValue() * ingredientEntry.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    // orders
    @JsonIgnore
    public List<Order> getAllOrders() {
        ArrayList<Order> result = new ArrayList<>();
        route.forEach(location -> result.addAll(location.getAllOrders()));
        return result;
    }

    // TODO check whether doing this here is appropriate!
    @Override
    public boolean equals(final Object obj) {
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