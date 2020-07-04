package de.thm.foodtruckbe.controller;

import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.Order;
import de.thm.foodtruckbe.services.OrderService;
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

    @Autowired
    OrderService orderService;

    @RequestMapping(path = "/getOrders", method = RequestMethod.POST)
    public void getOrdersByLocation(HttpServletRequest request, HttpServletResponse response){

    }

    @RequestMapping(path = "/getOrder", method = RequestMethod.GET)
    public Order getOrder(@RequestParam(value = "id") String id){
        return orderService.getOrderById(id);
    }

    @RequestMapping(path = "/getStatus", method = RequestMethod.GET)
    public Location.Status getOrderStatus(@RequestParam(value = "id") String id){
        return orderService.getOrderStatusById(id);
    }

    @RequestMapping(path = "/getPrice", method = RequestMethod.GET)
    public double getOrderPrice(@RequestParam(value = "id") String id){
        return orderService.getOrderPriceById(id);
    }

    @RequestMapping(path = "/setStatus", method = RequestMethod.POST)
    public void setOrderStatus(@RequestParam(value = "id") String id){
        orderService.setOrderStatusById(id);
    }

    @RequestMapping(path = "/createOrder", method = RequestMethod.GET)
    public void createOrder(@RequestParam(value = "id") String id){
        orderService.setOrderStatusById(id);
    }
}
