package de.thm.foodtruckbe.data.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import de.thm.foodtruckbe.Application;
import de.thm.foodtruckbe.data.dto.DtoLocation;
import de.thm.foodtruckbe.data.dto.user.DtoOperator;
import de.thm.foodtruckbe.data.entities.*;
import de.thm.foodtruckbe.data.entities.order.Order;
import de.thm.foodtruckbe.data.entities.order.PreOrder;
import de.thm.foodtruckbe.data.repos.LocationRepository;
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
    @JsonBackReference
    private List<Dish> preOrderMenu;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference
    private List<Dish> reservationMenu;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference(value = "operator-location")
    private List<Location> route;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @JsonIgnore
    private Location currentLocation;

    @Transient
    private Location initialLocation;

    @OneToMany(mappedBy = "operator")
    @JsonBackReference(value = "operator-ingredient")
    private List<Ingredient> stock;

    /**
     * Constructor for initial use. Sets the {@code Market} as the operators initial
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
    public Location addLocation(final Location location) {
        route.add(location);
        return location;
    }

    public List<Location> addLocations(final List<DtoLocation> dtoLocations) {
        List<Location> result = new ArrayList<>();
        for (DtoLocation dtoLocation : dtoLocations) {
            result.add(Location.create(dtoLocation, this, routeTail()));
        }
        return result;
    }

    public boolean removeLocation(final DtoLocation dtoLocation, LocationRepository locationRepository) {
        for (Location location : route) {
            if (dtoLocation.getName().equalsIgnoreCase(location.getName())) {
                locationRepository.findById(location.getId()).ifPresent(locationRepository::delete);
                locationRepository.delete(location);
                return route.remove(location);
            }
        }
        return false;
    }

    public boolean removeLocations(final List<DtoLocation> dtoLocations, LocationRepository locationRepository) {
        for (DtoLocation dtoLocation : dtoLocations) {
            if (removeLocation(dtoLocation, locationRepository)) {
                return false;
            }
        }
        return true;
    }

    // get location
    public Location getLocationFromRoute(Location location) {
        for (Location l : route) {
            if (l.equals(location)) {
                return l;
            }
        }
        throw new NotFoundException("Location " + location.getName() + "does not belong to this operator.");
    }

    public Location getLocationFromRoute(Long id) {
        for (Location l : route) {
            if (l.getId().equals(id)) {
                return l;
            }
        }
        throw new NotFoundException("Location with id " + id + "does not belong to this operator.");
    }

    public Location routeTail() {
        return route.get(route.size() - 1);
    }

    /**
     * Updates the route with the given location. Also adjusts the arrival- and departure-times of succeeding locations.
     *
     * @param updatedLocation
     * @param locationRepository
     * @return
     */
    public Location updateRoute(Location updatedLocation, LocationRepository locationRepository) {
        ArrayList<Location> routeOld = new ArrayList<>(route);
        route.clear();
        int index = 0;
        for (; index < routeOld.size(); index++) {
            if (!routeOld.get(index).getName().equals(updatedLocation.getName())) {
                route.add(routeOld.get(index));
            } else {
                if (index == 0) {
                    updatedLocation = Location.create(updatedLocation, this);
                } else {
                    updatedLocation = Location.create(updatedLocation, this, route.get(index - 1));
                }
                route.add(updatedLocation);
                break;
            }
        }
        index++;
        for (; index < routeOld.size(); index++) {
            Location location = routeOld.get(index).merge(Location.create(routeOld.get(index), this, route.get(index - 1)));
            route.add(location);
            locationRepository.save(location);
        }
        return updatedLocation;
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

    /**
     * Get the shopping-list based only on preorders.
     *
     * @return
     */
    @JsonIgnore
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

    /**
     * Go shopping with a list of ingredients(including preorders as well as extra portions).
     *
     * @param ingredients
     * @return
     */
    public List<Ingredient> goShopping(final List<Ingredient> ingredients) {
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
        return stock;
    }

    /**
     * Check if the given order is possible with the current stock.
     *
     * @param order
     * @return
     */
    public boolean isPossible(Order order) {
        for (DishWrapper dishWrapper : order.getItems()) {
            for (Ingredient ingredient : dishWrapper.getDish().getIngredients()) {
                if (dishWrapper.getAmount() * ingredient.getAmount() > stock.get(stock.indexOf(ingredient)).getAmount()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Check if the given dish is possible with the current stock.
     *
     * @param dish
     * @return
     */
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

    // preOrders
    @JsonIgnore
    public List<Order> getAllPreOrders() {
        ArrayList<Order> result = new ArrayList<>();
        route.forEach(location -> result.addAll(location.getAllPreOrders()));
        return result;
    }

    public static Operator create(DtoOperator dtoOperator) {
        return new Operator(dtoOperator.getName(), dtoOperator.getPassword());
    }

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