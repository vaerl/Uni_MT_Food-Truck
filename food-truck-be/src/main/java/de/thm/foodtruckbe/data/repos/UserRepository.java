package de.thm.foodtruckbe.data.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.thm.foodtruckbe.data.entities.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findUserById(Long id);

    Optional<User> findUserByNameIgnoreCase(String name);
}