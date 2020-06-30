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

    private String name;
    private Coordinates coordinates;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    /**
     * Constructor for initial {@code Location}. Needs an intial arrival-time.
     * 
     * @param name        the locations name
     * 
     * @param coordinates the current coordinates
     * 
     * @param arrival     the arrival-time of the food-truck
     * 
     * @param duration    the duration the food-truck stays
     */
    public Location(String name, Coordinates coordinates, final LocalDateTime arrival, final Duration duration) {
        this.name = name;
        this.coordinates = coordinates;
        this.arrival = arrival;
        this.departure = arrival.plus(duration);
    }

    /**
     * Constructor for {@code Locations} that follow an initial @code{Location}.
     * Automatically sets arrival date based upon the previous location and the new
     * coordinates.
     * 
     * @param previous    the preeciding location
     * 
     * @param name        the locations name
     * 
     * @param coordinates the current coordinates
     * 
     * @param duration    the duration the food-truck stays
     */
    public Location(String name, Location previous, Coordinates coordinates, final Duration duration) {
        this.name = name;
        this.coordinates = coordinates.calculateDistance(coordinates);
        this.arrival = previous.getDeparture().plus(previous.coordinates.calculateTravelTime(coordinates));
        this.departure = arrival.plus(duration);
    }

    // provide methods for setting delays

    /**
     * Adds the given duration to the arrival-time. Also adds the delay to the
     * departure time, as it is impacted by the arrival delay.
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

    @Override
    public String toString() {
        return name + coordinates + ": " + arrival + " - " + departure;
    }
}