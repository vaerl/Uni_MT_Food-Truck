package de.thm.foodtruckbe.entities.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import de.thm.foodtruckbe.entities.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    public Customer(String name, String password) {
        super(name, password);
    }

    public Customer(String name, String password, Location location) {
        this(name, password);
        this.location = location;
    }

    public List<Location> getNearestLocations(Operator operator) {
        if (location == null) {
            // TODO return List or throw exception?
            return new ArrayList<>();
        }
        return operator.getRoute().stream()
                .collect(Collectors.toMap(Function.identity(), e -> e.calculateDistance(location))).entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()).stream().map(Map.Entry::getKey)
                .limit(3).collect(Collectors.toList());
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