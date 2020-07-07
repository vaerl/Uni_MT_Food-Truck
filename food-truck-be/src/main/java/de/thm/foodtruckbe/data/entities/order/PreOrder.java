package de.thm.foodtruckbe.data.entities.order;

import java.util.Map;

import javax.persistence.Entity;

import de.thm.foodtruckbe.data.dto.order.DtoPreOrder;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class PreOrder extends Order {

    public PreOrder(Customer customer, Location location, Map<Dish, Integer> items) {
        super(customer, location, items);
    }

    public static PreOrder create(DtoPreOrder dtoPreOrder, Customer customer, Location location){
        return new PreOrder(customer, location, dtoPreOrder.getItems());
    }

}