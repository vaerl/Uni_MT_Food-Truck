package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.data.entities.Location;
import de.thm.foodtruckbe.data.repos.LocationRepository;
import de.thm.foodtruckbe.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

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

    @PostMapping(path = "/{id}/leave")
    public Duration leaveLocation(@RequestBody Duration duration, @PathVariable(value = "id") Long id) {
        Location currentLocation = getLocation(id);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        currentLocation.getOperator().moveToNextLocation();
                    }
                },
                duration.toMillis()
        );
        Location nextLocation = currentLocation.getOperator().getRoute().get(0);
        nextLocation.setArriving(duration.plus(currentLocation.calculateTravelTime(nextLocation)));
        locationRepository.save(nextLocation);
        currentLocation.setLeaving(duration);
        locationRepository.save(currentLocation);
        return duration;
    }

    @PostMapping(path = "/{id}/arrive")
    public Duration arriveAtLocation(@RequestBody Duration duration, @PathVariable(value = "id") Long id) {
        Location nextLocation = getLocation(id);
        Location currentLocation = nextLocation.getOperator().getCurrentLocation();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        currentLocation.getOperator().moveToNextLocation();
                    }
                },
                duration.toMillis()
        );
        nextLocation.setArriving(duration);
        locationRepository.save(nextLocation);
        Duration leave = duration.minus(currentLocation.calculateTravelTime(nextLocation));
        currentLocation.setLeaving(leave);
        locationRepository.save(currentLocation);
        return leave;
    }
}
