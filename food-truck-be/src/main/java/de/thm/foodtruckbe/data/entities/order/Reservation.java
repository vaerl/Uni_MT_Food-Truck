package de.thm.foodtruckbe.data.entities.order;

import java.util.Map;

import javax.persistence.Entity;

import de.thm.foodtruckbe.data.dto.order.DtoPreOrder;
import de.thm.foodtruckbe.data.dto.order.DtoReservation;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Reservation extends Order {

    public Reservation(Customer customer, Location location, Map<Dish, Integer> items) {
        super(customer, location, items);
    }

    public static Reservation create(DtoReservation dtoPreOrder, Customer customer, Location location){
        return new Reservation(customer, location, dtoPreOrder.getItems());
    }
}