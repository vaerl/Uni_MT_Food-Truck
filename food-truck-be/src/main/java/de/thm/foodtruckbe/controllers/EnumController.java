package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.order.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/enums")
public class EnumController {

    @GetMapping(path = "/ingredients")
    public List<String> getInrgedients() {
        ArrayList<String> ingredients = new ArrayList<>();
        for (Dish.Ingredient ingredient : Dish.Ingredient.class.getEnumConstants()) {
            String ingredientString = ingredient.toString().toLowerCase();
            ingredients.add(ingredientString.substring(0, 1).toUpperCase() + ingredientString.substring(1));
        }
        return ingredients;
    }

    @GetMapping(path = "/order/status")
    public List<String> getOrderStatus() {
        ArrayList<String> ingredients = new ArrayList<>();
        for (Order.Status status : Order.Status.class.getEnumConstants()) {
            String statusString = status.toString().toLowerCase();
            ingredients.add(statusString.substring(0, 1).toUpperCase() + statusString.substring(1));
        }
        return ingredients;
    }

    @GetMapping(path = "/location/status")
    public List<String> getLocationStatus() {
        ArrayList<String> ingredients = new ArrayList<>();
        for (Location.Status status : Location.Status.class.getEnumConstants()) {
            String statusString = status.toString().toLowerCase();
            ingredients.add(statusString.substring(0, 1).toUpperCase() + statusString.substring(1));
        }
        return ingredients;
    }
}
