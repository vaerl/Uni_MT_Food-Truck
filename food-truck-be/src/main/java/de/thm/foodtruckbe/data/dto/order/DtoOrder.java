package de.thm.foodtruckbe.data.dto.order;


import de.thm.foodtruckbe.data.dto.DtoDish;
import de.thm.foodtruckbe.data.dto.DtoLocation;
import de.thm.foodtruckbe.data.dto.user.DtoCustomer;
import de.thm.foodtruckbe.data.entities.Dish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public abstract class DtoOrder {

    private Long id;
    private DtoLocation dtoLocation;
    protected Map<Dish, Integer> items;
    protected DtoCustomer dtoCustomer;
    protected double price;
    protected Status status;

    public DtoOrder(Map<Dish, Integer> items) {
        this.items = items;
        this.price = new ArrayList<>(items.entrySet()).stream()
                .map(e -> e.getKey().getAdjustedPrice() * e.getValue()).reduce(0d, Double::sum);
        this.status = Status.ACCEPTED;
    }

    @Override
    public String toString() {
        return "Order:\n -> Price: " + price + "\n -> Status: " + status + "\n -> Items: "
                + new ArrayList<>(this.items.entrySet()).stream()
                .map(e -> e.getKey().getName() + " " + e.getValue()).reduce("", (a, b) -> a + ", " + b);
    }

    public enum Status {
        ACCEPTED, CONFIRMED, NOT_POSSIBLE, STARTED, DONE
    }
}
