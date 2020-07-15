package de.thm.foodtruckbe.data.entities.order;

import de.thm.foodtruckbe.data.dto.DtoDishWrapper;
import de.thm.foodtruckbe.data.dto.order.DtoPreOrder;
import de.thm.foodtruckbe.data.entities.DishWrapper;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.repos.DishRepository;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class PreOrder extends Order {

    public PreOrder(Customer customer, Location location) {
        super(customer, location);
    }

    public static PreOrder create(DtoPreOrder dtoPreOrder, Customer customer, Location location, DishRepository dishRepository) {
        PreOrder preOrder = new PreOrder(customer, location);
        List<DishWrapper> dishWrappers = new ArrayList<>();
        for (DtoDishWrapper dtoDishWrapper : dtoPreOrder.getItems()) {
            dishWrappers.add(DishWrapper.create(preOrder, dtoDishWrapper, getDish(dtoDishWrapper.getDish().getId(), dishRepository)));
        }
        preOrder.addAllItems(dishWrappers);
        return preOrder;
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
        price += dishWrapper.getDish().getBasePrice() * dishWrapper.getAmount();
        return true;
    }

    @Override
    public boolean removeItem(DishWrapper dishWrapper) {
        price -= dishWrapper.getDish().getBasePrice();
        return items.remove(dishWrapper);
    }

}