package de.thm.foodtruckbe.controller;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Dish;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public void getCustomers(){

    }

    @RequestMapping(path = "/{id}/locations", method = RequestMethod.GET)
    public List<Location> getNearestLocationsByCustomerIdAndOperatorId(
            @PathVariable(value = "id") String id,
            @RequestParam(value = "operatorId") String operatorId){
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable(value = "id") String id){
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public void createCustomer(@RequestBody Customer customer){
    }

}
