package com.example.foodtruck.model;

import com.example.foodtruck.model.order.PreOrder;
import com.example.foodtruck.model.order.Reservation;
import com.example.foodtruck.model.user.Operator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Location {

    private Long id;
    private String name;
    private Operator operator;
    private double x;
    private double y;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    private Status status;
    private Duration duration;

    private List<PreOrder> preOrders;

    private List<Reservation> reservations;

    public Location(final String name, final double x, final double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.preOrders = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.status = Status.OPEN;
    }

    public Location(final String name, final double x, final double y,
                    final Duration duration) {
        this(name, x, y);
        this.departure = arrival.plus(duration);
    }

    public Location(final String name, final Operator operator, final double x, final double y,
                    final LocalDateTime arrival, final Duration duration) {
        this(name, x, y);
        this.arrival = arrival;
        this.departure = arrival.plus(duration);
    }

    @Override
    public String toString() {
        return name + "(" + x + ", " + y + "): from " + arrival + " until " + departure;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Location) {
            return this.getName().equals(((Location) o).getName());
        }
        return false;
    }

    public enum Status {
        LEAVING, ARRIVING, CURRENT, CLOSED, OPEN
    }
}