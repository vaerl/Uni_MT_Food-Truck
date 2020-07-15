package de.thm.foodtruckbe.data.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
