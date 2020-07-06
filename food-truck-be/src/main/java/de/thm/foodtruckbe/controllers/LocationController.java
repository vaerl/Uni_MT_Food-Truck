package de.thm.foodtruckbe.controllers;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.entities.exceptions.EntityNotFoundException;
import de.thm.foodtruckbe.repos.LocationRepository;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private LocationRepository locationRepository;

    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public Location createNewLocation(@RequestBody Location location) {
        return locationRepository.save(location);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Location getLocationById(@PathVariable(value = "id") Long id) {
        var location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get();
        } else {
            throw new EntityNotFoundException("Customer", id);
        }
    }

    @RequestMapping(path = "/{id}/arrival", method = RequestMethod.GET)
    public LocalDateTime getArrivalTimeByLocationId(@PathVariable(value = "id") Long id) {
        var location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get().getArrival();
        } else {
            throw new EntityNotFoundException("Customer", id);
        }
    }

    @RequestMapping(path = "/{id}/departure", method = RequestMethod.GET)
    public LocalDateTime getDepartureTimeByLocationId(@PathVariable(value = "id") Long id) {
        var location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get().getDeparture();
        } else {
            throw new EntityNotFoundException("Customer", id);
        }
    }

    @RequestMapping(path = "/{id}/delay", method = RequestMethod.POST)
    public boolean setLocationDelayByLocationId(@RequestBody Duration duration, @PathVariable(value = "id") Long id) {
        var location = locationRepository.findById(id);
        if (location.isPresent()) {
            return location.get().setArrivalDelay(duration);
        } else {
            throw new EntityNotFoundException("Customer", id);
        }
    }
}
