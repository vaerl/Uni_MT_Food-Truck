package com.example.foodtruck.model.user;

import com.example.foodtruck.model.Dish;
import com.example.foodtruck.model.Location;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Operator extends User {

    private List<Dish> preOrderMenu;
    private List<Dish> reservationMenu;
    private List<Location> route;
    private Location currentLocation;
    private Map<Dish.Ingredient, Integer> stock;

    public Operator(String name, String password) {
        super(name, password);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof Operator) {
            return ((Operator) obj).getName().equals(this.getName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Operator " + name + ": \n-> currently at: " + currentLocation;
    }
}
