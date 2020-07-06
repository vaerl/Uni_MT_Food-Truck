package de.thm.foodtruckbe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.repos.CustomerRepository;
import de.thm.foodtruckbe.repos.OperatorRepository;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerRepository customerRespository;
    private OperatorRepository operatorRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, OperatorRepository operatorRepository) {
        this.customerRespository = customerRepository;
        this.operatorRepository = operatorRepository;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<Customer> getCustomers() {
        return customerRespository.findAll();
    }

    @RequestMapping(path = "/{id}/locations", method = RequestMethod.GET)
    public List<Location> getNearestLocationsByCustomerIdAndOperatorId(@PathVariable(value = "id") Long customerId,
            @RequestParam(value = "operatorId") Long operatorId) {
        var operator = operatorRepository.findById(operatorId);
        var customer = customerRespository.findById(customerId);
        if (operator.isPresent()) {
            if (customer.isPresent()) {
                return customer.get().getNearestLocations(operator.get());
            } else {
                throw new EntityNotFoundException("Customer", customerId);
            }
        } else {
            throw new EntityNotFoundException("Operator", operatorId);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable(value = "id") Long id) {
        var customer = customerRespository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new EntityNotFoundException("Customer", id);
        }

    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRespository.save(customer);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Customer updateCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customer) {
        var savedCustomer = customerRespository.findById(id);
        if (savedCustomer.isPresent()) {
            return savedCustomer.get().merge(customer);
        } else {
            throw new EntityNotFoundException("Customer", id);
        }
    }

}
