package de.thm.foodtruckbe.data.dto;


import de.thm.foodtruckbe.data.entities.Location;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DtoLocation {

    private Long id;
    private String name;
    private double x;
    private double y;
    private LocalDateTime arrival;
    private LocalDateTime departure;

    private Location.Status status;
    private Duration duration;

    public DtoLocation(final String name, final double x, final double y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.status = Location.Status.OPEN;
    }

    public Duration getDuration() {
        return Duration.between(arrival, departure);
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