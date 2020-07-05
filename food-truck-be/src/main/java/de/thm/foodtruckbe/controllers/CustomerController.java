package de.thm.foodtruckbe.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Location;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @RequestMapping(path = "/getCustomers", method = RequestMethod.POST)
    public void getCustomers(HttpServletRequest request, HttpServletResponse response) {
        // TODO implement
    }

    @RequestMapping(path = "/getNearestLocations", method = RequestMethod.GET)
    public List<Location> getNearestLocationsById(@RequestParam(value = "id") String id) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getCustomerById", method = RequestMethod.GET)
    public Customer getCustomerById(@RequestParam(value = "id") String id) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/createCustomer", method = RequestMethod.POST)
    public void createCustomer(@RequestBody Customer customer) {
        // TODO implement
    }

}
