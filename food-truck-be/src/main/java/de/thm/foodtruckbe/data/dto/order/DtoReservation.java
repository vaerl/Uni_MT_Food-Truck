package de.thm.foodtruckbe.data.dto.order;

import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DtoReservation extends DtoOrder {

    public DtoReservation(List<DtoDishWrapper> items) {
        super(items);
    }
}