package de.thm.foodtruckbe.data.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thm.foodtruckbe.Application;
import de.thm.foodtruckbe.data.dto.DtoLocation;
import de.thm.foodtruckbe.data.dto.user.DtoOperator;
import de.thm.foodtruckbe.data.entities.*;
import de.thm.foodtruckbe.data.entities.order.Order;
import de.thm.foodtruckbe.data.entities.order.PreOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.webjars.NotFoundException;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Operator extends User {

    @Transient
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Id
    @GeneratedValue
    @Column(name = "operator_id")
    private Long id;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference(value = "operator")
    private List<Dish> preOrderMenu;

    @OneToMany(mappedBy = "operator")
//    @JsonBackReference(value = "operator")
    private List<Dish> reservationMenu;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference
    private List<Location> route;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @JsonIgnore
    private Location currentLocation;

    @Transient
    private Location initialLocation;

    //    @ElementCollection
//    @CollectionTable(name = "ingredient_amount_mapping_stock")
//    @MapKeyEnumerated(EnumType.STRING)
//    @MapKeyClass(Ingredient.class)
//    @MapKeyColumn(name = "ingredient", nullable = false)
//    @Column(name = "amount")
//    private Map<Ingredient, Integer> stock;
    @OneToMany(mappedBy = "operator")
    @JsonBackReference
    private List<Ingredient> stock;

    /**
     * Constructor for inital use. Sets the {@code Market} as the operators initial
     * location with its start at 7AM and a duration of 30 minutes.
     *
     * @param name
     */
    public Operator(String name, String password) {
        super(name, password);
        this.preOrderMenu = new ArrayList<>();
        this.reservationMenu = new ArrayList<>();
        this.route = new ArrayList<>();
        double[] coordinates = Market.getCoordinates();
        this.currentLocation = new Location("Market", this, coordinates[0], coordinates[1],
                LocalDateTime.of(LocalDate.now(), LocalTime.of(7, 0, 0)), Duration.ofMinutes(30));
        this.initialLocation = this.currentLocation;
    }

    // TODO check whether removing/adding works correctly - maybe i need Log.d(TAG, "parseNetworkResponse: response: " + response.toString());orkwokto
    // TODO implement the respective equals-methods

    // methods for adding/removing dishes from menu
    public boolean addDishToMenu(final Dish dish) {
        if (isPossible(dish)) {
            reservationMenu.add(dish);
        }
        return preOrderMenu.add(dish);
    }

    public boolean updateMenu(Dish dish) {
        if (preOrderMenu.contains(dish)) {
            removeDishFromMenu(dish);
            return addDishToMenu(dish);
        } else {
            return false;
        }
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

    public boolean addLocations(final List<DtoLocation> dtoLocations) {
        for (DtoLocation dtoLocation : dtoLocations) {
            if (addLocation(Location.create(dtoLocation, this, route.get(route.size() - 1)))) {
                return false;
            }
        }
        return true;
    }

    public boolean removeLocation(final DtoLocation dtoLocation) {
        for (Location location : route) {
            if (dtoLocation.getName().equalsIgnoreCase(location.getName())) {
                return route.remove(location);
            }
        }
        return false;
    }

    public boolean removeLocations(final List<DtoLocation> dtoLocations) {
        for (DtoLocation dtoLocation : dtoLocations) {
            if (removeLocation(dtoLocation)) {
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

    public boolean leaveIn(Duration duration) {
        Location nextLocation = route.get(0);
        return nextLocation.setArriving(duration.plus(currentLocation.calculateTravelTime(nextLocation)))
                && currentLocation.setLeaving(duration);
    }

    // shopping
    @JsonIgnore
    // TODO accept Map<Ingredient, Integer>
    public ArrayList<Ingredient> getShoppingList() {
        ArrayList<Ingredient> results = new ArrayList<>();
        for (Location location : route) {
            for (PreOrder preOrder : location.getPreOrders()) {
                for (DishWrapper dishWrapper : preOrder.getItems()) {
                    for (Ingredient ingredient : dishWrapper.getDish().getIngredients()) {
                        if (results.contains(ingredient)) {
                            results.get(results.indexOf(ingredient)).addAmount(ingredient.getAmount() * dishWrapper.getAmount());
                        } else {
                            ingredient.setAmount(ingredient.getAmount() * dishWrapper.getAmount());
                        }
                        results.add(ingredient);
                    }
                }
            }
        }
        return results;
    }

    public boolean goShopping(final List<Ingredient> ingredients) {
        if (currentLocation != initialLocation) {
            return false;
        }
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
        moveToNextLocation();
        return true;
    }

    public boolean isPossible(Order order) {
        for (DishWrapper dishWrapper : order.getItems()) {
            for (Ingredient ingredient : dishWrapper.getDish().getIngredients()) {
                if (dishWrapper.getAmount() * ingredient.getAmount() > stock.get(stock.indexOf(ingredient)).getAmount()){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isPossible(final Dish dish) {
        for (Ingredient ingredient : dish.getIngredients()) {
            log.debug("In foreach in isPossible.");
            if (stock.contains(ingredient)) {
                if (ingredient.getAmount() > stock.get(stock.indexOf(ingredient)).getAmount()) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    // interact with stock
    public boolean addToStock(final Ingredient ingredient, int amount) {
        if (stock.contains(ingredient)) {
            stock.get(stock.indexOf(ingredient)).addAmount(amount);
        } else {
            stock.add(ingredient);
        }
        return true;
    }

    public boolean addToStock(List<DishWrapper> items) {
        for (DishWrapper dishWrapper : items) {
            for (Ingredient ingredient : dishWrapper.getDish().getIngredients()) {
                if (!addToStock(ingredient, dishWrapper.getAmount() * ingredient.getAmount())) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean removeFromStock(final Ingredient ingredient, final int amount) {
        if (stock.contains(ingredient)) {
            stock.get(stock.indexOf(ingredient)).subtractAmount(amount);
        } else {
            return false;
        }
        return true;
    }

    public boolean removeFromStock(List<DishWrapper> items) {
        for (DishWrapper dishWrapper : items) {
            for (Ingredient ingredient : dishWrapper.getDish().getIngredients()) {
                if (!removeFromStock(ingredient, dishWrapper.getAmount() * ingredient.getAmount())) {
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

    public static Operator create(DtoOperator dtoOperator) {
        return new Operator(dtoOperator.getName(), dtoOperator.getPassword());
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