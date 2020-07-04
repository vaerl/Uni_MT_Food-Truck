package de.thm.foodtruckbe.services;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    public List<Location> getNearestLocationsById(String id) {
        List<Location> locations = null;
        return locations;
    }

    public Customer getCustomerById(String id) {
        Customer customer = null;
        return customer;
    }

    public void createCustomer(Customer customer) {
    }
}
