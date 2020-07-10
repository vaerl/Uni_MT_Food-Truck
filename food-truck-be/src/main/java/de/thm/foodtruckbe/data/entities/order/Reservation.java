package de.thm.foodtruckbe.data.entities.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import de.thm.foodtruckbe.data.dto.order.DtoPreOrder;
import de.thm.foodtruckbe.data.dto.order.DtoReservation;
import de.thm.foodtruckbe.data.entities.DishWrapper;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.repos.IngredientRepository;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Reservation extends Order {

    public Reservation(Customer customer, Location location) {
        super(customer, location);
    }

    public static Reservation create(DtoReservation dtoReservation, Customer customer, Location location) {
        Reservation reservation = new Reservation(customer, location);
        List<DishWrapper> dishWrappers = new ArrayList<>();
        for (DtoDishWrapper dtoDishWrapper : dtoReservation.getItems()) {
            dishWrappers.add(DishWrapper.create(reservation, dtoDishWrapper, location.getOperator()));
        }
        reservation.addAllItems(dishWrappers);
        return reservation;
    }

    @Override
    public boolean addItem(DishWrapper dishWrapper) {
        if (dishWrapper.getAmount() <= 0) {
            return false;
        }
        // check if item is already present -> update amount
        if (items.contains(dishWrapper)) {
            items.get(items.indexOf(dishWrapper)).setAmount(items.get(items.indexOf(dishWrapper)).getAmount() + dishWrapper.getAmount());
        } else {
            items.add(dishWrapper);
        }
        price += dishWrapper.getDish().getAdjustedPrice() * dishWrapper.getAmount();
        return true;
    }

    @Override
    public boolean removeItem(DishWrapper dishWrapper) {
        price -= dishWrapper.getDish().getAdjustedPrice();
        return items.remove(dishWrapper);
    }
}