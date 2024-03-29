package de.thm.foodtruckbe.data.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoIngredient {

    private Long id;
    private String name;
    private int amount;

    public DtoIngredient(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void subtractAmount(int amount) {
        this.amount -= amount;
    }
}
