package de.thm.foodtruckbe.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import de.thm.foodtruckbe.entities.Dish.Ingredient;
import de.thm.foodtruckbe.entities.order.Order;
import de.thm.foodtruckbe.entities.order.PreOrder;
import de.thm.foodtruckbe.entities.order.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
// TODO add status
public class Location {

    // Time per unit in seconds - I assumed a velocity of 50 km/h.
    private static final double KILOMETERS_PER_HOUR = 50;

    @Id
    @GeneratedValue
    @Column(name = "location_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "operator_id", nullable = false)
    @JsonManagedReference
    private Operator operator;
    // Values in kilometers
    private double x;
    private double y;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    @OneToMany(mappedBy = "location")
    @JsonBackReference
    private List<PreOrder> preOrders;

    @OneToMany(mappedBy = "location")
    @JsonBackReference
    private List<Reservation> reservations;

    /**
     * Minimal constructor - for internal use or Customer-Location.
     * 
     * @param name the location's name
     * @param x
     * @param y
     */
    public Location(final String name, final Operator operator, final double x, final double y) {
        this.name = name;
        this.operator = operator;
        this.x = x;
        this.y = y;
        this.preOrders = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    /**
     * Constructor for initial {@code Location}. Needs an intial arrival-time.
     * {@code Operator} uses a {@code Market} as its first location.
     * 
     * @param name     the location's name
     * @param x
     * @param y
     * @param arrival  the arrival-time of the food-truck
     * @param duration the duration the food-truck stays
     */
    public Location(final String name, final Operator operator, final double x, final double y,
            final LocalDateTime arrival, final Duration duration) {
        this(name, operator, x, y);
        this.arrival = arrival;
        this.departure = arrival.plus(duration);
    }

    /**
     * Constructor for {@code Locations} that follow an initial @code{Location}.
     * Automatically sets arrival date based upon the previous location and the new
     * coordinates.
     * 
     * @param previous the previous location
     * @param name     the location's name
     * @param x
     * @param y
     * @param duration the duration the food-truck stays
     */
    public Location(final String name, final Operator operator, final double x, final double y, final Location previous,
            final Duration duration) {
        this(name, operator, x, y);
        this.arrival = previous.getDeparture().plus(previous.calculateTravelTime(this));
        this.departure = arrival.plus(duration);
    }

    // methods for setting delays
    /**
     * Adds the given duration to the arrival-time. Also adds the delay to the
     * departure time, as it is impacted by the arrival-delay.
     * 
     * @param duration delay for the arrival time
     * @return success of operation
     */
    public boolean setArrivalDelay(final Duration duration) {
        arrival = arrival.plus(duration);
        setDepartureDelay(duration);
        return true;
    }

    /**
     * Adds the given delay to only the departure time.
     * 
     * @param duration delay for the arrival time
     * @return success of operation
     */
    public boolean setDepartureDelay(final Duration duration) {
        departure = departure.plus(duration);
        return true;
    }

    /**
     * Subtracts the given duration from both arrival- and departure-time.
     * 
     * @param duration lead for the arrival time
     * @return
     */
    public boolean setArrivalLead(final Duration duration) {
        arrival = arrival.minus(duration);
        setDepartureLead(duration);
        return true;
    }

    /**
     * Subtracts the given duration from the departure-time.
     * 
     * @param duration lead for the arrival time
     * @return
     */
    public boolean setDepartureLead(final Duration duration) {
        departure = departure.minus(duration);
        return true;
    }

    // coordinate-methods
    /**
     * Calculates the Distance from a to b.
     * 
     * @param b destination-coordinates
     * @return length of the distance between a and b
     */
    public double calculateDistance(final Location b) {
        return Math.sqrt(Math.pow(b.x - this.x, 2) + Math.pow(b.y - this.y, 2));
    }

    /**
     * Calculates the travel-time from a to b.
     * 
     * @param b destination-location
     * @return a Duration based on the assumed velocity {@code KILOMETERS_PER_HOUR}
     */
    public Duration calculateTravelTime(final Location b) {
        return Duration.ofSeconds((long) (calculateDistance(b) / (KILOMETERS_PER_HOUR / 3600)));
    }

    /**
     * Calculates the travel-time from a to b.
     * 
     * @param b                 destination-location
     * @param kilometersPerHour the food-trucks average verlocity
     * @return a Duration based on the given velocity {@code kilometersPerHour}
     */
    public Duration calculateTravelTime(final Location b, final double kilometersPerHour) {
        return Duration.ofSeconds((long) (calculateDistance(b) / (kilometersPerHour / 3600)));
    }

    // orders
    @JsonIgnore
    public List<Order> getAllOrders() {
        final ArrayList<Order> result = new ArrayList<>();
        result.addAll(preOrders);
        result.addAll(reservations);
        return result;
    }

    // preOrders
    public boolean addPreOrder(final PreOrder preOrder) {
        if (isBeforeNextDay()) {
            return false;
        }
        return preOrders.add(preOrder);
    }

    public boolean addAllPreOrders(final List<PreOrder> preOrders) {
        for (final PreOrder preOrder : preOrders) {
            if (!addPreOrder(preOrder)) {
                return false;
            }
        }
        return true;
    }

    public boolean removePreOrder(final PreOrder preOrder) {
        if (isBeforeNextDay()) {
            return false;
        }
        return preOrders.remove(preOrder);
    }

    // reservations
    public boolean addReservation(final Reservation reservation) {
        if (!isPossible(reservation)) {
            return false;
        }
        return this.reservations.add(reservation);
    }

    public boolean addAllReservations(final List<Reservation> reservations) {
        for (final Reservation reservation : reservations) {
            if (!addReservation(reservation)) {
                return false;
            }
        }
        return true;
    }

    // remove reservation
    public boolean removeReservation(final Reservation reservation) {
        // remove reservation
        if (this.reservations.remove(reservation)) {
            // update stock
            operator.addToStock(reservation.getItems());
            return true;
        } else {
            return false;
        }
    }

    // check orders
    private boolean isPossible(final Order order) {
        for (final Map.Entry<Dish, Integer> dishEntry : order.getItems().entrySet()) {
            for (final Map.Entry<Ingredient, Integer> ingredientEntry : dishEntry.getKey().getIngredients()
                    .entrySet()) {
                if (dishEntry.getValue() * ingredientEntry.getValue() > operator.getStock()
                        .get(ingredientEntry.getKey())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPossible(final Dish dish) {
        for (final Map.Entry<Ingredient, Integer> ingredient : dish.getIngredients().entrySet()) {
            if (ingredient.getValue() > operator.getStock().get(ingredient.getKey())) {
                return false;
            }
        }
        return true;
    }

    public boolean isBeforeNextDay() {
        return LocalDateTime.now().isBefore(LocalDateTime.of(arrival.toLocalDate().plusDays(1), LocalTime.of(7, 0, 0)));
    }

    @Override
    public String toString() {
        return name + "(" + x + ", " + y + "): from " + arrival + " until " + departure;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Location) {
            return this.getName().equals(((Location) o).getName());
        }
        return false;
    }

    public enum Status {
        FLOUR, BUTTER, BREAD, PORK, OIL, FRIES, SALT, PEPPER, WHEAT, TOMATO, EGGS
    }
}