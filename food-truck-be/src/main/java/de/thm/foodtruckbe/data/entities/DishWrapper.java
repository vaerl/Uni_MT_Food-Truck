package de.thm.foodtruckbe.data.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import de.thm.foodtruckbe.data.entities.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DishWrapper {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dish_id", nullable = false)
    @JsonManagedReference(value = "dish-dishwrapper")
    private Dish dish;

    private int amount;

    /**
     * Wrapper, combines a dish with an amount.
     *
     * @param order
     * @param dish
     * @param amount
     */
    public DishWrapper(Order order, Dish dish, int amount) {
        this.order = order;
        this.dish = dish;
        this.amount = amount;
    }

    public static DishWrapper create(Order order, DtoDishWrapper dtoDishWrapper, Dish dish) {
        return new DishWrapper(order, dish, dtoDishWrapper.getAmount());
    }
}
