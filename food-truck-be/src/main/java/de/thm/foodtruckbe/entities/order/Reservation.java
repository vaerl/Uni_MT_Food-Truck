package de.thm.foodtruckbe.entities.order;

import java.util.Map;

import javax.persistence.Entity;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Reservation extends Order {

    public Reservation(Customer customer, Location location, Map<Dish, Integer> items) {
        super(customer, location, items);
    }
}