package de.thm.foodtruckbe.data.dto.order;

import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import de.thm.foodtruckbe.data.entities.DishWrapper;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DtoPreOrder extends DtoOrder {

    public DtoPreOrder(List<DtoDishWrapper> items) {
        super(items);
    }

}
