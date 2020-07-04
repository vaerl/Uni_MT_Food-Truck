package de.thm.foodtruckbe.controller;

import de.thm.foodtruckbe.entities.Location;
import de.thm.foodtruckbe.services.CustomerService;
import de.thm.foodtruckbe.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    LocationService locationService;

    @RequestMapping(path = "/getLocations", method = RequestMethod.GET)
    public void getCustomers(HttpServletRequest request, HttpServletResponse response){

    }

    @RequestMapping(path = "/createNewLocation", method = RequestMethod.POST)
    public void createNewLocation(@RequestBody Location location){
        locationService.createNewLocation(location);
    }

    @RequestMapping(path = "/getLocationByName", method = RequestMethod.GET)
    public Location getLocationByName(@RequestParam(value = "locationName") String locationName){
        return locationService.getLocationByName(locationName);
    }

    @RequestMapping(path = "/getArrivalTimeByLocationName", method = RequestMethod.GET)
    public LocalDateTime getArrivalTimeByLocationName(@RequestParam(value = "locationName") String locationName){
        return locationService.getArrivalTimeByLocationName(locationName);
    }

    @RequestMapping(path = "/getDepartureTimeByLocationName", method = RequestMethod.GET)
    public LocalDateTime getDepartureTimeByLocationName(@RequestParam(value = "locationName") String locationName){
        return locationService.getDepartureTimeByLocationName(locationName);
    }

    @RequestMapping(path = "/setLocationDelay", method = RequestMethod.POST)
    public void setLocationDelay(@RequestParam(value = "locationName") String locationName){
        locationService.setLocationDelay(locationName);
    }

    @RequestMapping(path = "/getDistance", method = RequestMethod.GET)
    public double getDistance(@RequestParam(value = "locationNameFrom") String locationNameFrom,
                                                        @RequestParam(value = "locationNameTo") String locationNameTo){
        return locationService.getDistance(locationNameFrom, locationNameTo);
    }

}
