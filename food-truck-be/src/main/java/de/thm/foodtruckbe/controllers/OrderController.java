package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.data.entities.order.Order;
import de.thm.foodtruckbe.data.entities.order.Order.Status;
import de.thm.foodtruckbe.data.repos.OrderRepository;
import de.thm.foodtruckbe.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
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
    public boolean setOrderStatusById(@PathVariable(value = "id") Long id, @RequestBody Status status) {
        Order order = getOrder(id);
        order.setStatus(status);
        orderRepository.save(order);
        return true;
    }
}
