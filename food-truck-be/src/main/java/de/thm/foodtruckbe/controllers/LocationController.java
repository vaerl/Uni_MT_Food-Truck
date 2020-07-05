package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @RequestMapping(path = "/getLocations", method = RequestMethod.GET)
    public void getCustomers(HttpServletRequest request, HttpServletResponse response) {
        // TODO implement
    }

    @RequestMapping(path = "/createNewLocation", method = RequestMethod.POST)
    public void createNewLocation(@RequestBody Location location) {
        // TODO implement
    }

    @RequestMapping(path = "/getLocationByName", method = RequestMethod.GET)
    public Location getLocationByName(@RequestParam(value = "locationName") String locationName) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getArrivalTimeByLocationName", method = RequestMethod.GET)
    public LocalDateTime getArrivalTimeByLocationName(@RequestParam(value = "locationName") String locationName) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/getDepartureTimeByLocationName", method = RequestMethod.GET)
    public LocalDateTime getDepartureTimeByLocationName(@RequestParam(value = "locationName") String locationName) {
        // TODO implement
        return null;
    }

    @RequestMapping(path = "/setLocationDelay", method = RequestMethod.POST)
    public void setLocationDelay(@RequestParam(value = "locationName") String locationName) {
        // TODO implement
    }

    @RequestMapping(path = "/getDistance", method = RequestMethod.GET)
    public double getDistance(@RequestParam(value = "locationNameFrom") String locationNameFrom,
            @RequestParam(value = "locationNameTo") String locationNameTo) {
        // TODO implement
        return 0.0;
    }

}
