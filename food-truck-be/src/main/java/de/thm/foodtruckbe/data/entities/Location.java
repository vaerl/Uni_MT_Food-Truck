package de.thm.foodtruckbe.data.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.dto.DtoLocation;
import de.thm.foodtruckbe.data.entities.order.Order;
import de.thm.foodtruckbe.data.entities.order.PreOrder;
import de.thm.foodtruckbe.data.entities.order.Reservation;
import de.thm.foodtruckbe.data.entities.user.Operator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    @JsonManagedReference(value = "operator-location")
    private Operator operator;
    // Values in kilometers
    private double x;
    private double y;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Duration duration;

    @OneToMany(mappedBy = "location")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    @JsonBackReference
    private List<PreOrder> preOrders;

    @OneToMany(mappedBy = "location")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
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
        this.status = Status.OPEN;
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
    public Location(final String name, final Operator operator, final double x, final double y, LocalDateTime arrival, final Location previous,
                    final Duration duration) {
        this(name, operator, x, y);
        if (arrival == null || arrival.isBefore(previous.getDeparture().plus(previous.calculateTravelTime(this)))) {
            this.arrival = previous.getDeparture().plus(previous.calculateTravelTime(this));
        } else {
            this.arrival = arrival;
        }
        this.departure = this.arrival.plus(duration);
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

    public double calculateDistance(double x, double y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2));
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
    @JsonIgnore
    public List<Order> getAllPreOrders() {
        final ArrayList<Order> result = new ArrayList<>(preOrders);
        return result;
    }

    // preOrders
    public boolean addPreOrder(final PreOrder preOrder) {
        if (isBeforeNextDay() || status == Status.CLOSED || status == Status.LEAVING) {
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
        if (!isPossible(reservation) || status == Status.CLOSED || status == Status.LEAVING) {
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
        for (DishWrapper dishWrapper : order.getItems()) {
            for (Ingredient ingredient : dishWrapper.getDish().getIngredients()) {
                if (dishWrapper.getAmount() * ingredient.getAmount() > operator.getStock()
                        .get(operator.getStock().indexOf(ingredient)).getAmount()) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isPossible(final Dish dish) {
        for (Ingredient ingredient : dish.getIngredients()) {
            if (ingredient.getAmount() > operator.getStock()
                    .get(operator.getStock().indexOf(ingredient)).getAmount()) {
                return false;
            }
        }
        return true;
    }

    @JsonIgnore
    public boolean isBeforeNextDay() {
        return LocalDateTime.now().isBefore(LocalDateTime.of(arrival.toLocalDate().plusDays(1), LocalTime.of(7, 0, 0)));
    }

    public static Location create(DtoLocation dtoLocation, Operator operator, Location location) {
        return new Location(dtoLocation.getName(), operator, dtoLocation.getX(), dtoLocation.getY(), dtoLocation.getArrival(), location, dtoLocation.getDuration());
    }

    public static Location create(Location location, Operator operator, Location previous) {
        return new Location(location.getName(), operator, location.getX(), location.getY(),location.getArrival(), previous, location.getDuration());
    }

    public static Location create(Location location, Operator operator) {
        return new Location(location.getName(), operator, location.getX(), location.getY(),location.getArrival(), location.getDuration());
    }

    @JsonIgnore
    public Duration getDuration(){
        return Duration.between(arrival, departure);
    }

    public Location merge(Location location) {
        this.name = location.name;
        this.x = location.x;
        this.y = location.y;
        this.arrival = location.arrival;
        this.departure = location.departure;
        return this;
    }

    public Location merge(DtoLocation dtoLocation) {
        this.name = dtoLocation.getName();
        this.x = dtoLocation.getX();
        this.y = dtoLocation.getY();
        this.arrival = dtoLocation.getArrival();
        this.departure = dtoLocation.getDeparture();
        return this;
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

    // status
    public boolean setArriving(Duration duration) {
        this.status = Status.ARRIVING;
        this.duration = duration;
        return true;
    }

    public boolean setLeaving(Duration duration) {
        this.status = Status.LEAVING;
        this.duration = duration;
        return true;
    }

    public enum Status {
        LEAVING, ARRIVING, CURRENT, CLOSED, OPEN
    }
}