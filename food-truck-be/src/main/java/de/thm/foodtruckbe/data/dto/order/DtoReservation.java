package de.thm.foodtruckbe.data.dto.order;

import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import de.thm.foodtruckbe.data.dto.user.DtoCustomer;
import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.DishWrapper;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class DtoReservation extends DtoOrder {

    public DtoReservation(List<DtoDishWrapper> items) {
        super(items);
    }
}