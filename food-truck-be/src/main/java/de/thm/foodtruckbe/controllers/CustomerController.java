package de.thm.foodtruckbe.controllers;

import java.util.List;
import java.util.Optional;

import de.thm.foodtruckbe.data.dto.user.DtoCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.user.Operator;
import de.thm.foodtruckbe.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.data.repos.CustomerRepository;
import de.thm.foodtruckbe.data.repos.OperatorRepository;

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
        Optional<Operator> operator = operatorRepository.findById(id);
        if (operator.isPresent()) {
            return operator.get();
        } else {
            throw new EntityNotFoundException("Operator", id);
        }
    }

    public Customer getCustomer(Long id) {
        Optional<Customer> customer = customerRespository.findById(id);
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
            @RequestParam(value = "operatorId") Long operatorId, @RequestParam(value = "x") double x,@RequestParam(value = "y") double y) {
        return getCustomer(customerId).getNearestLocations(getOperator(operatorId),x ,y);
    }

    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Long id) {
        return getCustomer(id);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer createCustomer(@RequestBody DtoCustomer dtoCustomer) {
        return customerRespository.save(Customer.create(dtoCustomer));
    }

    @PutMapping(path = "/{id}")
    public Customer updateCustomer(@PathVariable(value = "id") Long id, @RequestBody Customer customer) {
        return getCustomer(id).merge(customer);
    }

}
