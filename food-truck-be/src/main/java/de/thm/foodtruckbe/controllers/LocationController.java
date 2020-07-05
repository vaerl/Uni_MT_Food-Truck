package de.thm.foodtruckbe.controllers;

import de.thm.foodtruckbe.entities.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    // @RequestMapping(path = "/all", method = RequestMethod.GET)
    // public void getLocationsByOperatorId(@RequestParam(value = "operatorId")
    // String locationName) {
    // }

    // @RequestMapping(path = "/create", method = RequestMethod.POST)
    // public void createNewLocation(@RequestBody Location location) {
    // }

    // @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    // public Location getLocationById(@PathVariable(value = "id") String id) {
    // }

    // @RequestMapping(path = "/{id}/arrival", method = RequestMethod.GET)
    // public LocalDateTime getArrivalTimeByLocationId(@PathVariable(value = "id")
    // String id) {
    // }

    // @RequestMapping(path = "/{id}/departure", method = RequestMethod.GET)
    // public LocalDateTime getDepartureTimeByLocationId(@PathVariable(value = "id")
    // String id) {
    // }

    // @RequestMapping(path = "/{id}/delay", method = RequestMethod.POST)
    // public void setLocationDelayByLocationId(@RequestBody Duration duration,
    // @PathVariable(value = "id") String id) {
    // }

    // // Die Art der Parameter bei disem Endpunkt muss/kann angepasst werden...
    // @RequestMapping(path = "/{id}/distance", method = RequestMethod.GET)
    // public double getDistanceByLocationIdAndLocation(@PathVariable(value = "id")
    // String id,
    // @RequestBody Location location) {
    // }

}
