package de.thm.foodtruckbe.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.user.Operator;
import de.thm.foodtruckbe.entities.user.User;

@RestController
@RequestMapping("/api/user")
public class UserController {

    public User getOperator(Long id) {
        // Optional<Operator> operator = operatorRepository.findById(id);
        // if (operator.isPresent()) {
        // return operator.get();
        // } else {
        // throw new EntityNotFoundException("Operator", id);
        // }
        return null;
    }

}