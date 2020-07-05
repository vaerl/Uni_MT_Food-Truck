package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    @RequestMapping(path = "/getMenuByOperatorName", method = RequestMethod.GET)
    public List<Dish> getMenuByOperatorName(@RequestParam(value = "name") String name) {
        return null;
    }

    @RequestMapping(path = "/getRouteByOperatorName", method = RequestMethod.GET)
    public List<Location> getRouteByOperatorName(@RequestParam(value = "name") String name) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getCurrentLocationByOperatorName", method = RequestMethod.GET)
    public Location getCurrentLocationByOperatorName(@RequestParam(value = "name") String name) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getShoppingListByOperatorName", method = RequestMethod.GET)
    public Map<Dish.Ingredient, Integer> getShoppingListByOperatorName(@RequestParam(value = "name") String name) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getOrdersForLocationByOperatorNameAndLocation", method = RequestMethod.GET)
    public List<Dish> getOrdersForLocationByOperatorNameAndLocation(
            @RequestParam(value = "operatorName") String operatorName,
            @RequestParam(value = "locationName") String locationName) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getAllOrdersByOperatorName", method = RequestMethod.GET)
    public List<Dish> getAllOrdersByOperatorName(@RequestParam(value = "operatorName") String operatorName) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/addOrderForLocationByOperatorName", method = RequestMethod.POST)
    public void addOrderForLocationByOperatorName(@RequestBody List<Dish> order,
            @RequestParam(value = "operatorName") String operatorName,
            @RequestParam(value = "locationName") String locationName) {
        // TODO implement
    }

    @RequestMapping(path = "/addLocationsToRouteByOperatorName", method = RequestMethod.POST)
    public void addLocationsToRouteByOperatorName(@RequestBody List<Location> locations,
            @RequestParam(value = "operatorName") String operatorName) {
        // TODO implement
    }

    @RequestMapping(path = "/removeLocationsFromRouteByOperatorName", method = RequestMethod.POST)
    public void removeLocationsToRouteByOperatorName(@RequestBody List<Location> locations,
            @RequestParam(value = "operatorName") String operatorName) {
        // TODO implement
    }

    @RequestMapping(path = "/addDishToMenuByOperatorName", method = RequestMethod.POST)
    public void addDishToMenuByOperatorName(@RequestBody Dish dish,
            @RequestParam(value = "operatorName") String operatorName) {
        // TODO implement
    }

    @RequestMapping(path = "/removeDishFromMenuByOperatorName", method = RequestMethod.POST)
    public void removeDishFromMenuByOperatorName(@RequestBody Dish dish,
            @RequestParam(value = "operatorName") String operatorName) {
        // TODO implement
    }
}
