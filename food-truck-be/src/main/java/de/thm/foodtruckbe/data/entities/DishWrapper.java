package de.thm.foodtruckbe.data.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import de.thm.foodtruckbe.data.entities.user.Operator;
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
    @JsonManagedReference
    private Dish dish;

    private int amount;

    public DishWrapper(Dish dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

    public static DishWrapper create(DtoDishWrapper dtoDishWrapper, Operator operator){
        return new DishWrapper(Dish.create(dtoDishWrapper.getDish(), operator), dtoDishWrapper.getAmount());
    }
}
