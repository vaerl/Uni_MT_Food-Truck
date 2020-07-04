package de.thm.foodtruckbe.entities;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Location {

    // Time per unit in seconds - I assumed a velocity of 50 km/h.
    private static final double KILOMETERS_PER_HOUR = 50;

    private String name;
    // Values in kilometers
    private double x;
    private double y;

    private LocalDateTime arrival;
    private LocalDateTime departure;

    /**
     * Minimal constructor - for internal use only.
     * 
     * @param name the location's name
     * @param x
     * @param y
     */
    private Location(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor for initial {@code Location}. Needs an intial arrival-time.
     * 
     * @param name     the location's name
     * @param x
     * @param y
     * @param arrival  the arrival-time of the food-truck
     * @param duration the duration the food-truck stays
     */
    public Location(String name, double x, double y, final LocalDateTime arrival, final Duration duration) {
        this(name, x, y);
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
    public Location(String name, double x, double y, Location previous, final Duration duration) {
        this(name, x, y);
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
    public boolean setArrivalDelay(Duration duration) {
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
    public boolean setDepartureDelay(Duration duration) {
        departure = departure.plus(duration);
        return true;
    }

    /**
     * Subtracts the given duration from both arrival- and departure-time.
     * 
     * @param duration lead for the arrival time
     * @return
     */
    public boolean setArrivalLead(Duration duration) {
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
    public boolean setDepartureLead(Duration duration) {
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
    public double calculateDistance(Location b) {
        return Math.sqrt(Math.pow(b.x - this.x, 2) + Math.pow(b.y - this.y, 2));
    }

    /**
     * Calculates the travel-time from a to b.
     * 
     * @param b destination-location
     * @return a Duration based on the assumed velocity {@code KILOMETERS_PER_HOUR}
     */
    public Duration calculateTravelTime(Location b) {
        return Duration.ofSeconds((long) (calculateDistance(b) / (KILOMETERS_PER_HOUR / 3600)));
    }

    /**
     * Calculates the travel-time from a to b.
     * 
     * @param b                 destination-location
     * @param kilometersPerHour the food-trucks average verlocity
     * @return a Duration based on the given velocity {@code kilometersPerHour}
     */
    public Duration calculateTravelTime(Location b, double kilometersPerHour) {
        return Duration.ofSeconds((long) (calculateDistance(b) / (kilometersPerHour / 3600)));
    }

    @Override
    public String toString() {
        return name + "(" + x + ", " + y + "): from " + arrival + " until " + departure;
    }

    public enum Status {
        FLOUR, BUTTER, BREAD, PORK, OIL, FRIES, SALT, PEPPER, WHEAT, TOMATO, EGGS
    }
}