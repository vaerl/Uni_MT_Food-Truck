package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    // @RequestMapping(path = "/all", method = RequestMethod.GET)
    // public List<Order> getAllOrdersByOperatorId(@RequestParam(value =
    // "operatorId") String operatorId) {
    // }

    // @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    // public Order getOrderById(@PathVariable(value = "id") String id) {
    // }

    // @RequestMapping(path = "/{id}/status", method = RequestMethod.GET)
    // public Order.Status getOrderStatusById(@PathVariable(value = "id") String id)
    // {
    // }

    // @RequestMapping(path = "/{id}/price", method = RequestMethod.GET)
    // public double getOrderPriceById(@PathVariable(value = "id") String id) {
    // }

    // @RequestMapping(path = "/{id}/status", method = RequestMethod.POST)
    // public void setOrderStatusById(@PathVariable(value = "id") String id) {
    // }

    // @RequestMapping(path = "/create", method = RequestMethod.POST)
    // public void createOrder(@RequestBody Order order) {
    // }
}
