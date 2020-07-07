package de.thm.foodtruckbe.data.entities.order;

import java.util.Map;

import javax.persistence.Entity;

import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Customer;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PersonalOrder extends Order {

    public PersonalOrder(Customer customer, Location location, Map<Dish, Integer> items) {
        super(customer, location, items);
    }
}