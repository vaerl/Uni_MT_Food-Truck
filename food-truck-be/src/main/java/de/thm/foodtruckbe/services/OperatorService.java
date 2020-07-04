package de.thm.foodtruckbe.services;

import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperatorService {
    public List<Dish> getMenuByOperatorName(String name) {
        List<Dish> menu = null;
        return menu;
    }

    public List<Location> getRouteByOperatorName(String name) {
        List<Location> route = null;
        return route;
    }

    public Location getCurrentLocationByOperatorName(String name) {
        Location location = null;
        return location;
    }

    public Map<Dish.Ingredient, Integer> getShoppingListByOperatorName(String name) {
        Map<Dish.Ingredient, Integer> shoppingList = null;
        return shoppingList;
    }

    public List<Dish> getOrdersForLocationByOperatorNameAndLocation(String operatorName, String locationName) {
        List<Dish> orders = null;
        return orders;
    }

    public List<Dish> getAllOrdersByOperatorName(String operatorName) {
        List<Dish> orders = null;
        return orders;
    }

    public void addOrderForLocationByOperatorName(List<Dish> order, String operatorName, String locationName) {
    }

    public void addLocationsToRouteByOperatorName(List<Location> locations, String operatorName) {
    }

    public void removeLocationsToRouteByOperatorName(List<Location> locations, String operatorName) {
    }

    public void addDishToMenuByOperatorName(Dish dish, String operatorName) {
    }

    public void removeDishFromMenuByOperatorName(Dish dish, String operatorName) {
    }
}
