package de.thm.foodtruckbe.data.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.thm.foodtruckbe.data.entities.Dish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
public class DtoDishWrapper {

    private Long id;
    private DtoDish dish;

    private int amount;

    public DtoDishWrapper(DtoDish dish, int amount) {
        this.dish = dish;
        this.amount = amount;
    }

}
