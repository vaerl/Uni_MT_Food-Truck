package de.thm.foodtruckbe.entities;

import java.time.Duration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreOrder extends Order {

    private static String LATEST_POSSIBLE_TIME_String = "12 (work-)hours before arrival at Location";
    private static Duration LATEST_POSSIBLE_TIME = Duration.ofHours(8);

    private Location location;

    public PreOrder(Dish dish, int amount, Location location) {
        super(dish, amount);
        this.location = location;
    }

}