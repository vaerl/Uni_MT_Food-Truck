package de.thm.foodtruckbe.data.entities.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import de.thm.foodtruckbe.data.dto.user.DtoCustomer;
import de.thm.foodtruckbe.data.entities.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {

    public Customer(String name, String password) {
        super(name, password);
    }

    public List<Location> getNearestLocations(Operator operator, double x, double y) {
        return operator.getRoute().stream()
                .collect(Collectors.toMap(Function.identity(), e -> e.calculateDistance(x, y))).entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()).stream().map(Map.Entry::getKey)
                .limit(3).collect(Collectors.toList());
    }

    public static Customer create(DtoCustomer dtoCustomer) {
        return new Customer(dtoCustomer.getName(), dtoCustomer.getPassword());
    }

    @Override
    public String toString() {
        return super.name;
    }

    public Customer merge(Customer customer) {
        this.name = customer.name;
        return this;
    }
}