package de.thm.foodtruckbe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.entities.order.Order;
import de.thm.foodtruckbe.entities.order.Order.Status;
import de.thm.foodtruckbe.repos.OrderRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrder(Long id) {
        var order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        } else {
            throw new EntityNotFoundException("Order", id);
        }
    }

    @GetMapping(path = "/{id}")
    public Order getOrderById(@PathVariable(value = "id") Long id) {
        return getOrder(id);
    }

    @GetMapping(path = "/{id}/status")
    public Order.Status getOrderStatusById(@PathVariable(value = "id") Long id) {
        return getOrder(id).getStatus();
    }

    @GetMapping(path = "/{id}/price")
    public double getOrderPriceById(@PathVariable(value = "id") Long id) {
        return getOrder(id).getPrice();
    }

    @PostMapping(path = "/{id}/status")
    public void setOrderStatusById(@PathVariable(value = "id") Long id, @RequestBody Status status) {
        getOrder(id).setStatus(status);
    }
}
