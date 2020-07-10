package de.thm.foodtruckbe.data.dto.order;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import de.thm.foodtruckbe.data.dto.DtoLocation;
import de.thm.foodtruckbe.data.dto.user.DtoCustomer;
import de.thm.foodtruckbe.data.entities.DishWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public abstract class DtoOrder {

    private Long id;
    private DtoLocation dtoLocation;

    protected List<DtoDishWrapper> items;
    protected DtoCustomer dtoCustomer;
    protected double price;
    protected Status status;

    public DtoOrder(List<DtoDishWrapper> items) {
        this.items = items;
        this.price = items.stream()
                .map(e -> e.getDish().getAdjustedPrice() * e.getAmount()).reduce(0d, Double::sum);
        this.status = Status.ACCEPTED;
    }

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}
