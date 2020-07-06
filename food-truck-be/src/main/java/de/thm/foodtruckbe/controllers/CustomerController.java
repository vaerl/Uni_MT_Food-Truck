package de.thm.foodtruckbe.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.Customer;
import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.Operator;
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

    public Operator getOperator(Long id) {
        var operator = operatorRepository.findById(id);
        if (operator.isPresent()) {
            return operator.get();
        } else {
            throw new EntityNotFoundException("Operator", id);
        }
    }

    public Customer getCustomer(Long id) {
        var customer = customerRespository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new EntityNotFoundException("Customer", id);
        }
    }

    @GetMapping(path = "/all")
    public List<Customer> getCustomers() {
        return customerRespository.findAll();
    }

    @PostMapping(path = "/{id}/locations")
    public List<Location> getNearestLocationsByCustomerIdAndOperatorId(@PathVariable(value = "id") Long customerId,
            @RequestParam(value = "operatorId") Long operatorId) {
        return getCustomer(customerId).getNearestLocations(getOperator(operatorId));
    }

    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Long id) {
        return getCustomer(id);

    }

    @PostMapping(path = "/")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRespository.save(customer);
    }

    @PutMapping(path = "/{id}")
    public Customer updateCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customer) {
        return getCustomer(id).merge(customer);
    }

}
