package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.order.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @RequestMapping(path = "/getOrders", method = RequestMethod.POST)
    public void getOrdersByLocation(HttpServletRequest request, HttpServletResponse response) {
        // TODO implement
    }

    @RequestMapping(path = "/getOrder", method = RequestMethod.GET)
    public Order getOrder(@RequestParam(value = "id") String id) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getStatus", method = RequestMethod.GET)
    public Location.Status getOrderStatus(@RequestParam(value = "id") String id) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getPrice", method = RequestMethod.GET)
    public double getOrderPrice(@RequestParam(value = "id") String id) {
        // TODO implement
        return 0.0;
    }

    @RequestMapping(path = "/setStatus", method = RequestMethod.POST)
    public void setOrderStatus(@RequestParam(value = "id") String id) {
        // TODO implement
    }

    @RequestMapping(path = "/createOrder", method = RequestMethod.GET)
    public void createOrder(@RequestParam(value = "id") String id) {
        // TODO implement
    }
}
