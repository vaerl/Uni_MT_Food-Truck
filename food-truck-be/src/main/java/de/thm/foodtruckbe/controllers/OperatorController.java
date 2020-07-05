package de.thm.foodtruckbe.controller;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    @RequestMapping(path = "/{id}/menu", method = RequestMethod.GET)
    public List<Dish> getMenuByOperatorId(@PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/route", method = RequestMethod.GET)
    public List<Location> getRouteByOperatorId(@PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/location", method = RequestMethod.GET)
    public Location getCurrentLocationByOperatorId(@PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/shopping-list", method = RequestMethod.GET)
    public Map<Dish.Ingredient, Integer> getShoppingListByOperatorId(@PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/orders/{locationId}", method = RequestMethod.GET)
    public List<Dish> getOrdersForLocationByOperatorIdAndLocationId(@PathVariable(value = "id") String id,
                                                                    @PathVariable(value = "locationId") String locationId){
    }

    @RequestMapping(path = "/{id}/orders}", method = RequestMethod.GET)
    public List<Dish> getAllOrdersByOperatorId(@PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/orders/{locationId}/add", method = RequestMethod.POST)
    public void addOrderForLocationByOperatorIdAndLocationid(@RequestBody List<Dish> order,
                                                  @PathVariable(value = "id") String id,
                                                  @PathVariable(value = "locationId") String locationId){
    }

    @RequestMapping(path = "/{id}/route/add", method = RequestMethod.POST)
    public void addLocationsToRouteByOperatorId(@RequestBody List<Location> locations,
                                                @PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/route/remove", method = RequestMethod.POST)
    public void removeLocationsFromRouteByOperatorId(@RequestBody List<Location> locations,
                                                     @PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/menu/add", method = RequestMethod.POST)
    public void addDishToMenuByOperatorId(@RequestBody Dish dish,
                                          @PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/{id}/menu/remove", method = RequestMethod.POST)
    public void removeDishFromMenuByOperatorId(@RequestBody Dish dish,
                                               @PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void createOperator(@RequestBody Operator operator){
    }

    @RequestMapping(path = "/{id}/dishes/{dishId}/rating", method = RequestMethod.GET)
    public List<Location> getDishRatingByDishId(@PathVariable(value = "id") String id,
                                                @PathVariable(value = "dishId") String dishId){
    }

    @RequestMapping(path = "/{id}/dishes/{dishId}/rating", method = RequestMethod.POST)
    public void setDishRatingByDishId(@PathVariable(value = "id") String id,
                                      @PathVariable(value = "dishId") String dishId,
                                      @RequestParam(value = "rating") String rating){
    }

    @RequestMapping(path = "/{id}/dishes/{dishId}/delete", method = RequestMethod.POST)
    public void deleteDish(@PathVariable(value = "id") String id,
                           @PathVariable(value = "dishId") String dishId){
    }

    @RequestMapping(path = "/{id}/dishes/create", method = RequestMethod.POST)
    public void createDish(@RequestBody Dish dish){
    }
}
