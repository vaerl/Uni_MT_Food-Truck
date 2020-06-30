package de.thm.foodtruckbe.entities;

import java.time.Duration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coordinates {

    // Time per unit in seconds - I assumed a velocity of 50 km/h.
    private static final double KILOMETERS_PER_HOUR = 50;

    // Values in kilometers
    private double x;
    private double y;
    private double length;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
        this.length = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    /**
     * Calculates the Distance from a to b.
     * 
     * @param b destination-coordinates
     * @return Coordinates representing the distance between coordinates a and
     *         b(basically a vector).
     */
    public Coordinates calculateDistance(Coordinates b) {
        return new Coordinates(b.x - this.x, b.y - this.y);
    }

    /**
     * Calculates the travel-time from a to b.
     * 
     * @param b destination-coordinates
     * @return a Duration based on the assumed velocity {@code KILOMETERS_PER_HOUR}
     */
    public Duration calculateTravelTime(Coordinates b) {
        return Duration.ofSeconds((long) (calculateDistance(b).length / (KILOMETERS_PER_HOUR / 3600)));
    }

    /**
     * Calculates the travel-time from a to b.
     * 
     * @param b                 destination-coordinates
     * @param kilometersPerHour the food-trucks average verlocity
     * @return a Duration based on the given velocity {@code kilometersPerHour}
     */
    public Duration calculateTravelTime(Coordinates b, double kilometersPerHour) {
        return Duration.ofSeconds((long) (this.calculateDistance(b).length / (kilometersPerHour / 3600)));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}