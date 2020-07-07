package de.thm.foodtruckbe.data.dto.user;

import java.util.List;
import java.util.Map;

import de.thm.foodtruckbe.data.dto.DtoDish;
import de.thm.foodtruckbe.data.dto.DtoLocation;
import de.thm.foodtruckbe.data.entities.Dish;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DtoOperator extends DtoUser {

    private List<DtoDish> preOrderMenu;
    private List<DtoDish> reservationMenu;
    private List<DtoLocation> route;
    private DtoLocation currentLocation;
    private Map<Dish.Ingredient, Integer> stock;

    public DtoOperator(String name, String password) {
        super(name, password);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof DtoOperator) {
            return ((DtoOperator) obj).getName().equals(this.getName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Operator " + name + ": \n-> currently at: " + currentLocation;
    }
}
