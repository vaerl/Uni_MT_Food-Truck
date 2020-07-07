package de.thm.foodtruckbe.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.thm.foodtruckbe.entities.order.PreOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private Location location;

    public Customer(String name) {
        this.name = name;
    }

    public Customer(String name, Location location) {
        this(name);
        this.location = location;
    }

    public List<Location> getNearestLocations(Operator operator) {
        if (location == null) {
            return new ArrayList<>();
        }
        return operator.getRoute().stream()
                .collect(Collectors.toMap(Function.identity(), e -> e.calculateDistance(this.location))).entrySet()
                .stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()).stream()
                .map(Map.Entry::getKey).limit(3).collect(Collectors.toList());
    }

    public List<Location> getNearestLocations(Location location, Operator operator) {
        return operator.getRoute().stream()
                .collect(Collectors.toMap(Function.identity(), e -> e.calculateDistance(location))).entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList()).stream().map(Map.Entry::getKey)
                .limit(3).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return name;
    }
}