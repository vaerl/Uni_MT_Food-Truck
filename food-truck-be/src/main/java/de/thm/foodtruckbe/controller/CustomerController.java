package de.thm.foodtruckbe.controller;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(path = "/getCustomers", method = RequestMethod.POST)
    public void getCustomers(HttpServletRequest request, HttpServletResponse response){

    }

    @RequestMapping(path = "/getNearestLocations", method = RequestMethod.GET)
    public List<Location> getNearestLocationsById(@RequestParam(value = "id") String id){
        return customerService.getNearestLocationsById(id);
    }

    @RequestMapping(path = "/getCustomerById", method = RequestMethod.GET)
    public Customer getCustomerById(@RequestParam(value = "id") String id){
        return customerService.getCustomerById(id);
    }

    @RequestMapping(path = "/createCustomer", method = RequestMethod.POST)
    public void createCustomer(@RequestBody Customer customer){
        customerService.createCustomer(customer);
    }

}
