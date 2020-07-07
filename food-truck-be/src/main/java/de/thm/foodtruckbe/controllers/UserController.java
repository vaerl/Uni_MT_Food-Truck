package de.thm.foodtruckbe.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.data.entities.user.User;
import de.thm.foodtruckbe.data.repos.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("Operator", id);
        }
    }

    @PostMapping(path = "/login")
    public User login(@RequestBody User user) {
        log.info(user.toString());
        Optional<User> savedUser = userRepository.findUserByNameIgnoreCase(user.getName());
        if (savedUser.isPresent()) {
            return savedUser.get();
        } else {
            throw new EntityNotFoundException("Operator", user.getName());
        }
    }
}