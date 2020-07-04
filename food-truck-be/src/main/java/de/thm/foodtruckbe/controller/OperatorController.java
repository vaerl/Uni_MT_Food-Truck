package de.thm.foodtruckbe.controller;

import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.services.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    @Autowired
    OperatorService operatorService;

    @RequestMapping(path = "/getMenuByOperatorName", method = RequestMethod.GET)
    public List<Dish> getMenuByOperatorName(@RequestParam(value = "name") String name){
        return operatorService.getMenuByOperatorName(name);
    }

    @RequestMapping(path = "/getRouteByOperatorName", method = RequestMethod.GET)
    public List<Location> getRouteByOperatorName(@RequestParam(value = "name") String name){
        return operatorService.getRouteByOperatorName(name);
    }

    @RequestMapping(path = "/getCurrentLocationByOperatorName", method = RequestMethod.GET)
    public Location getCurrentLocationByOperatorName(@RequestParam(value = "name") String name){
        return operatorService.getCurrentLocationByOperatorName(name);
    }

    @RequestMapping(path = "/getShoppingListByOperatorName", method = RequestMethod.GET)
    public Map<Dish.Ingredient, Integer> getShoppingListByOperatorName(@RequestParam(value = "name") String name){
        return operatorService.getShoppingListByOperatorName(name);
    }

    @RequestMapping(path = "/getOrdersForLocationByOperatorNameAndLocation", method = RequestMethod.GET)
    public List<Dish> getOrdersForLocationByOperatorNameAndLocation(@RequestParam(value = "operatorName") String operatorName,
                                                                       @RequestParam(value = "locationName") String locationName){
        return operatorService.getOrdersForLocationByOperatorNameAndLocation(operatorName, locationName);
    }

    @RequestMapping(path = "/getAllOrdersByOperatorName", method = RequestMethod.GET)
    public List<Dish> getAllOrdersByOperatorName(@RequestParam(value = "operatorName") String operatorName){
        return operatorService.getAllOrdersByOperatorName(operatorName);
    }

    @RequestMapping(path = "/addOrderForLocationByOperatorName", method = RequestMethod.POST)
    public void addOrderForLocationByOperatorName(@RequestBody List<Dish> order,
                                                 @RequestParam(value = "operatorName") String operatorName,
                                                 @RequestParam(value = "locationName") String locationName){
        operatorService.addOrderForLocationByOperatorName(order, operatorName, locationName);
    }

    @RequestMapping(path = "/addLocationsToRouteByOperatorName", method = RequestMethod.POST)
    public void addLocationsToRouteByOperatorName(@RequestBody List<Location> locations,
                                                  @RequestParam(value = "operatorName") String operatorName){
        operatorService.addLocationsToRouteByOperatorName(locations, operatorName);
    }

    @RequestMapping(path = "/removeLocationsFromRouteByOperatorName", method = RequestMethod.POST)
    public void removeLocationsToRouteByOperatorName(@RequestBody List<Location> locations,
                                                 @RequestParam(value = "operatorName") String operatorName){
        operatorService.removeLocationsToRouteByOperatorName(locations, operatorName);
    }

    @RequestMapping(path = "/addDishToMenuByOperatorName", method = RequestMethod.POST)
    public void addDishToMenuByOperatorName(@RequestBody Dish dish,
                                                     @RequestParam(value = "operatorName") String operatorName){
        operatorService.addDishToMenuByOperatorName(dish, operatorName);
    }

    @RequestMapping(path = "/removeDishFromMenuByOperatorName", method = RequestMethod.POST)
    public void removeDishFromMenuByOperatorName(@RequestBody Dish dish,
                                            @RequestParam(value = "operatorName") String operatorName){
        operatorService.removeDishFromMenuByOperatorName(dish, operatorName);
    }
}
