package de.thm.foodtruckbe.controllers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.repos.LocationRepository;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location getLocation(Long id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get();
        } else {
            throw new EntityNotFoundException("Location", id);
        }
    }

    @PostMapping(path = "/")
    public Location createNewLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    @GetMapping(path = "/{id}")
    public Location getLocationById(@PathVariable(value = "id") Long id) {
        return getLocation(id);
    }

    @GetMapping(path = "/{id}/arrival")
    public LocalDateTime getArrivalTimeByLocationId(@PathVariable(value = "id") Long id) {
        return getLocation(id).getArrival();
    }

    @GetMapping(path = "/{id}/departure")
    public LocalDateTime getDepartureTimeByLocationId(@PathVariable(value = "id") Long id) {
        return getLocation(id).getDeparture();
    }

    @PostMapping(path = "/{id}/delay")
    public boolean setLocationDelayByLocationId(@RequestBody Duration duration, @PathVariable(value = "id") Long id) {
        return getLocation(id).setArrivalDelay(duration);
    }
}
