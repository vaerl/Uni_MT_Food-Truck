package de.thm.foodtruckbe.data.dto;


import de.thm.foodtruckbe.data.dto.order.DtoPreOrder;
import de.thm.foodtruckbe.data.dto.order.DtoReservation;
import de.thm.foodtruckbe.data.dto.user.DtoOperator;
import de.thm.foodtruckbe.data.entities.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DtoLocation {

    private Long id;
    private String name;
    private DtoOperator dtoOperator;
    private double x;
    private double y;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    private Location.Status status;
    private Duration duration;

    private List<DtoPreOrder> preOrders;

    private List<DtoReservation> reservations;

    public DtoLocation(final String name, final double x, final double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.preOrders = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.status = Location.Status.OPEN;
    }

    public DtoLocation(final String name, final double x, final double y,
                       final Duration duration) {
        this(name, x, y);
        this.departure = arrival.plus(duration);
    }

    public DtoLocation(final String name, final DtoOperator dtoOperator, final double x, final double y,
                       final LocalDateTime arrival, final Duration duration) {
        this(name, x, y);
        this.arrival = arrival;
        this.dtoOperator = dtoOperator;
        this.departure = arrival.plus(duration);
    }

    @Override
    public String toString() {
        return name + "(" + x + ", " + y + "): from " + arrival + " until " + departure;
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof DtoLocation) {
            return this.getName().equals(((DtoLocation) o).getName());
        }
        return false;
    }
}