package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.data.dto.user.DtoCustomer;
import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.entities.order.Order;
import de.thm.foodtruckbe.data.entities.user.Customer;
import de.thm.foodtruckbe.data.entities.user.Operator;
import de.thm.foodtruckbe.data.repos.CustomerRepository;
import de.thm.foodtruckbe.data.repos.OperatorRepository;
import de.thm.foodtruckbe.data.repos.OrderRepository;
import de.thm.foodtruckbe.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerRepository customerRespository;
    private OperatorRepository operatorRepository;
    private OrderRepository orderRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository, OperatorRepository operatorRepository, OrderRepository orderRepository) {
        this.customerRespository = customerRepository;
        this.operatorRepository = operatorRepository;
        this.orderRepository = orderRepository;
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
                                                                       @RequestParam(value = "operatorId") Long operatorId, @RequestBody double[] array) {
        return getCustomer(customerId).getNearestLocations(getOperator(operatorId), array[0], array[1]);
    }

    @GetMapping(path = "/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Long id) {
        return getCustomer(id);
    }

    @GetMapping(path = "/{id}/orders")
    public List<Order> getCustomerOrdersById(@PathVariable(value = "id") Long id) {
        return orderRepository.findAllByCustomerId(id);
    }

    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer createCustomer(@RequestBody DtoCustomer dtoCustomer) {
        return customerRespository.save(Customer.create(dtoCustomer));
    }

    @PutMapping(path = "/{id}")
    public Customer updateCustomer(@PathVariable(value = "id") Long id, @RequestBody DtoCustomer dtoCustomer) {
        return getCustomer(id).merge(Customer.create(dtoCustomer));
    }

}
