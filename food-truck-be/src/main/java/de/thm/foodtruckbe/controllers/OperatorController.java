package de.thm.foodtruckbe.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import de.thm.foodtruckbe.Application;
import de.thm.foodtruckbe.data.dto.DtoDish;
import de.thm.foodtruckbe.data.dto.DtoLocation;
import de.thm.foodtruckbe.data.dto.order.DtoPreOrder;
import de.thm.foodtruckbe.data.dto.order.DtoReservation;
import de.thm.foodtruckbe.data.dto.user.DtoOperator;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.repos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.data.entities.Dish;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Operator;
import de.thm.foodtruckbe.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.data.entities.order.Order;
import de.thm.foodtruckbe.data.entities.order.PreOrder;
import de.thm.foodtruckbe.data.entities.order.Reservation;

@RestController
@RequestMapping("/api/operator")
public class OperatorController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private CustomerRepository customerRespository;
    private OperatorRepository operatorRepository;
    private LocationRepository locationRepository;
    private DishRepository dishRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OperatorController(OperatorRepository operatorRepository, LocationRepository locationRepository,
                              DishRepository dishRepository, OrderRepository orderRepository, CustomerRepository customerRespository) {
        this.operatorRepository = operatorRepository;
        this.locationRepository = locationRepository;
        this.dishRepository = dishRepository;
        this.orderRepository = orderRepository;
        this.customerRespository = customerRespository;
    }

    public Operator getOperator(Long id) {
        Optional<Operator> operator = operatorRepository.findById(id);
        if (operator.isPresent()) {
            return operator.get();
        } else {
            throw new EntityNotFoundException("Operator", id);
        }
    }

    public Location getLocation(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get();
        } else {
            throw new EntityNotFoundException("Location", id);
        }
    }

    public Dish getDish(Long id) {
        Optional<Dish> dish = dishRepository.findById(id);
        if (dish.isPresent()) {
            return dish.get();
        } else {
            throw new EntityNotFoundException("Dish", id);
        }
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> customer = customerRespository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new EntityNotFoundException("Customer", id);
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
        log.info("OperatorController: /" + id + "/location");
        log.info("OperatorController: Result:" + getOperator(id).getCurrentLocation());
        return getOperator(id).getCurrentLocation();
    }

    @GetMapping(path = "/{id}/shopping-list")
    public Map<Dish.Ingredient, Integer> getShoppingListByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getShoppingList();
    }

    @PostMapping(path = "/{id}/shopping")
    public boolean goShopping(@PathVariable(value = "id") Long id, @RequestBody Map<Dish.Ingredient, Integer> ingredients) {
        return getOperator(id).goShopping(ingredients);
    }

    @GetMapping(path = "/{id}/orders/{locationId}")
    public List<Order> getAllOrdersForLocationByOperatorIdAndLocationId(@PathVariable(value = "id") Long operatorId,
                                                                        @PathVariable(value = "locationId") Long locationId) {
        return getOperator(operatorId).getLocation(getLocation(locationId)).getAllOrders();
    }

    @GetMapping(path = "/{id}/orders")
    public List<Order> getAllOrdersByOperatorId(@PathVariable(value = "id") Long id) {
        return getOperator(id).getAllOrders();
    }

    @PostMapping(path = "/{id}/orders/{locationId}/{customerId}/preorders")
    public boolean addPreOrderForLocationByOperatorIdLocationidAndCustomerId(@RequestBody List<DtoPreOrder> dtoPreOrders,
                                                                             @PathVariable(value = "id") Long operatorId, @PathVariable(value = "locationId") Long locationId, @PathVariable(value = "customerId") Long customerId) {
        for (DtoPreOrder dtoPreOrder : dtoPreOrders) {
            PreOrder preOrder = PreOrder.create(dtoPreOrder, getCustomer(customerId), getLocation(locationId));
            getOperator(operatorId).getLocation(getLocation(locationId)).addPreOrder(preOrder);
            orderRepository.save(preOrder);
        }
        return true;
    }

    @PostMapping(path = "/{id}/orders/{locationId}/{customerId}/reservation")
    public boolean addReservationsForLocationByOperatorIdLocationidAndCustomerId(@RequestBody List<DtoReservation> dtoReservations,
                                                                                 @PathVariable(value = "id") Long operatorId, @PathVariable(value = "locationId") Long locationId, @PathVariable(value = "customerId") Long customerId) {
        for (DtoReservation dtoReservation : dtoReservations) {
            Reservation reservation = Reservation.create(dtoReservation, getCustomer(customerId), getLocation(locationId));
            getOperator(operatorId).getLocation(getLocation(locationId)).addReservation(reservation);
            orderRepository.save(reservation);
        }
        return true;
    }

    @PostMapping(path = "/{id}/route")
    public boolean addLocationsToRouteByOperatorId(@RequestBody List<DtoLocation> dtoLocations,
                                                   @PathVariable(value = "id") Long id) {
        return getOperator(id).addLocations(dtoLocations);
    }

    @PostMapping(path = "/{id}/route/next")
    public boolean moveToNext(@PathVariable(value = "id") Long id) {
        return getOperator(id).moveToNextLocation();
    }

    @DeleteMapping(path = "/{id}/route")
    public boolean removeLocationsFromRouteByOperatorId(@RequestBody List<DtoLocation> dtoLocations,
                                                        @PathVariable(value = "id") Long id) {
        return getOperator(id).removeLocations(dtoLocations);
    }

    @DeleteMapping(path = "/{id}/menu")
    public boolean removeDishFromMenuByOperatorId(@RequestBody Dish dish, @PathVariable(value = "id") Long id) {
        return getOperator(id).removeDishFromMenu(dish);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Operator createOperator(@RequestBody DtoOperator dtoOperator) {
        return operatorRepository.save(Operator.create(dtoOperator));
    }

    @GetMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Operator getOperatorById(@PathVariable(value = "id") Long id) {
        return getOperator(id);
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

    @PostMapping(path = "/{id}/dishes/{dishId}/update")
    public Dish updateDish(@PathVariable(value = "id") Long operatorId,
                           @PathVariable(value = "dishId") Long dishId, @RequestBody DtoDish dtoDish) {
        Operator operator = getOperator(operatorId);
        Dish dish = operator.getDishFromMenu(getDish(dishId));
        dish = dish.merge(Dish.create(dtoDish, operator));
        operator.updateMenu(dish);
        return dishRepository.save(dish);
    }

    @PostMapping(path = "/{id}/dishes/{dishId}/adjustPrice")
    public void adjustDishPrice(@PathVariable(value = "id") Long operatorId,
                                @PathVariable(value = "dishId") Long dishId, @RequestBody Double adjustedPrice) {
        Operator operator = getOperator(operatorId);
        Dish dish = operator.getDishFromMenu(getDish(dishId));
        dish.setAdjustedPrice(adjustedPrice);
        dishRepository.save(getDish(dish.getId()).merge(dish));
    }

    @DeleteMapping(path = "/{id}/dishes/{dishId}")
    public boolean deleteDishAndOperatorId(@PathVariable(value = "id") Long operatorId,
                                           @PathVariable(value = "dishId") Long dishId) {
        Dish dish = getDish(dishId);
        dishRepository.delete(dish);
        return getOperator(operatorId).removeDishFromMenu(dish);
    }

    @PostMapping(path = "/{id}/dishes")
    public Dish createDishByOperatorId(@PathVariable(value = "id") Long operatorId, @RequestBody DtoDish dtoDish) {
        Operator operator = getOperator(operatorId);
        Dish savedDish = dishRepository.save(Dish.create(dtoDish, operator));
        operator.addDishToMenu(savedDish);
        return savedDish;
    }
}
