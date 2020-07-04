package de.thm.foodtruckbe.services;

import de.thm.foodtruckbe.entities.Location.Status;
import de.thm.foodtruckbe.entities.order.Order;

import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public Status getOrderStatusById(String id) {
        return status;
    }

    public double getOrderPriceById(String id) {
        double price = 0;
        return price;
    }

    public Order getOrderById(String id) {
        Order order = null;
        return order;
    }

    public void setOrderStatusById(String id) {
    }
}
