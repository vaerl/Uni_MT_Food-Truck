package de.thm.foodtruckbe.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.user.Operator;
import de.thm.foodtruckbe.entities.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.entities.order.Order;
import de.thm.foodtruckbe.entities.order.PreOrder;
import de.thm.foodtruckbe.entities.order.Reservation;
import de.thm.foodtruckbe.repos.DishRepository;
import de.thm.foodtruckbe.repos.LocationRepository;
import de.thm.foodtruckbe.repos.OperatorRepository;
import de.thm.foodtruckbe.repos.OrderRepository;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    private OperatorRepository operatorRepository;
    private LocationRepository locationRepository;
    private DishRepository dishRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OperatorController(OperatorRepository operatorRepository, LocationRepository locationRepository,
            DishRepository dishRepository, OrderRepository orderRepository) {
        this.operatorRepository = operatorRepository;
        this.locationRepository = locationRepository;
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
    }

    public Operator getOperator(Long id) {
        var operator = operatorRepository.findById(id);
        if (operator.isPresent()) {
            return operator.get();
        } else {
            throw new EntityNotFoundException("Operator", id);
        }
    }

    public Location getLocation(Long id) {
        var location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get();
        } else {
            throw new EntityNotFoundException("Location", id);
        }
    }

    public Dish getDish(Long id) {
        var dish = dishRepository.findById(id);
        if (dish.isPresent()) {
            return dish.get();
        } else {
            throw new EntityNotFoundException("Dish", id);
        }
    }

    @GetMapping(path = "/{id}/menu/preorder")
    public List<Dish> getPreOrderMenuByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getPreOrderMenu();
    }

    @GetMapping(path = "/{id}/menu/reservation")
    public List<Dish> getReservationMenuByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getReservationMenu();
    }

    @GetMapping(path = "/{id}/route")
    public List<Location> getRouteByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getRoute();
    }

    @GetMapping(path = "/{id}/location")
    public Location getCurrentLocationByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getCurrentLocation();
    }

    @GetMapping(path = "/{id}/shopping-list")
    public Map<Dish.Ingredient, Integer> getShoppingListByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getShoppingList();
    }

    @GetMapping(path = "/{id}/orders/{locationId}")
    public List<Order> getAllOrdersForLocationByOperatorIdAndLocationId(@PathVariable(value = "id") Long operatorId,
            @PathVariable(value = "locationId") Long locationId) {
        return getOperator(operatorId).getLocation(getLocation(locationId)).getAllOrders();
    }

    @GetMapping(path = "/{id}/orders}")
    public List<Order> getAllOrdersByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getAllOrders();
    }

    @PostMapping(path = "/{id}/orders/{locationId}/preorders")
    public boolean addPreOrderForLocationByOperatorIdAndLocationid(@RequestBody List<PreOrder> preOrders,
            @PathVariable(value = "id") Long operatorId, @PathVariable(value = "locationId") Long locationId) {
        orderRepository.saveAll(preOrders);
        return getOperator(operatorId).getLocation(getLocation(locationId)).addAllPreOrders(preOrders);
    }

    @PostMapping(path = "/{id}/orders/{locationId}/reservations")
    public boolean addReservationsForLocationByOperatorIdAndLocationid(@RequestBody List<Reservation> reservations,
            @PathVariable(value = "id") Long operatorId, @PathVariable(value = "locationId") Long locationId) {
        orderRepository.saveAll(reservations);
        return getOperator(operatorId).getLocation(getLocation(locationId)).addAllReservations(reservations);
    }

    @PostMapping(path = "/{id}/route/")
    public boolean addLocationsToRouteByOperatorId(@RequestBody List<Location> locations,
            @PathVariable(value = "id") Long id) {
        return getOperator(id).addLocations(locations);
    }

    @DeleteMapping(path = "/{id}/route/")
    public boolean removeLocationsFromRouteByOperatorId(@RequestBody List<Location> locations,
            @PathVariable(value = "id") Long id) {
        return getOperator(id).removeLocations(locations);
    }

    @PostMapping(path = "/{id}/menu")
    public boolean addDishToMenuByOperatorId(@RequestBody Dish dish, @PathVariable(value = "id") Long id) {
        return getOperator(id).addDishToMenu(dish);
    }

    @DeleteMapping(path = "/{id}/menu/")
    public boolean removeDishFromMenuByOperatorId(@RequestBody Dish dish, @PathVariable(value = "id") Long id) {
        return getOperator(id).removeDishFromMenu(dish);
    }

    @PostMapping(path = "/")
    public Operator createOperator(@RequestBody Operator operator) {
        return operatorRepository.save(operator);
    }

    @GetMapping(path = "/{id}/dishes/{dishId}/rating")
    public double getDishRatingByDishIdAndOperatorId(@PathVariable(value = "id") Long operatorId,
            @PathVariable(value = "dishId") Long dishId) {
        return getOperator(operatorId).getDishFromMenu(getDish(dishId)).getRating();
    }

    @PostMapping(path = "/{id}/dishes/{dishId}/rating")
    public void setDishRatingByDishIdAndOperatorId(@PathVariable(value = "id") Long operatorId,
            @PathVariable(value = "dishId") Long dishId, @RequestParam(value = "rating") Double rating) {
        getOperator(operatorId).getDishFromMenu(getDish(dishId)).setRating(rating);
    }

    @PostMapping(path = "/{id}/dishes/{dishId}/rate")
    public void addDishRatingByDishIdAndOperatorId(@PathVariable(value = "id") Long operatorId,
            @PathVariable(value = "dishId") Long dishId, @RequestParam(value = "rating") Integer rating) {
        getOperator(operatorId).getDishFromMenu(getDish(dishId)).addRating(rating);
    }

    @DeleteMapping(path = "/{id}/dishes/{dishId}/")
    public boolean deleteDishAndOperatorId(@PathVariable(value = "id") Long operatorId,
            @PathVariable(value = "dishId") Long dishId) {

        var dish = getDish(dishId);
        dishRepository.delete(dish);
        return getOperator(operatorId).removeDishFromMenu(dish);
    }

    @PostMapping(path = "/{id}/dishes/")
    public Dish createDishAndOperatorId(@PathVariable(value = "id") Long operatorId, @RequestBody Dish dish) {
        var savedDish = dishRepository.save(dish);
        getOperator(operatorId).addDishToMenu(savedDish);
        return savedDish;
    }
}
