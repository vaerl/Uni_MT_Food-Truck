package de.thm.foodtruckbe.data.dto.order;

import de.thm.foodtruckbe.data.dto.user.DtoCustomer;
import de.thm.foodtruckbe.data.entities.Dish;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
public class DtoPreOrder extends DtoOrder {

    public DtoPreOrder(DtoCustomer customer, Map<Dish, Integer> items) {
        super(customer, items);
    }

}
