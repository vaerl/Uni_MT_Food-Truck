package de.thm.foodtruckbe.services;

import de.thm.foodtruckbe.entities.Location;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationService {
    
    public void createNewLocation(Location location) {
    }

    public Location getLocationByName(String locationName) {
        Location location = null;
        return location;
    }

    public LocalDateTime getArrivalTimeByLocationName(String locationName) {
        LocalDateTime localDateTime = null;
        return localDateTime;
    }

    public LocalDateTime getDepartureTimeByLocationName(String locationName) {
        LocalDateTime localDateTime = null;
        return localDateTime;
    }

    public void setLocationDelay(String locationName) {
    }

    public double getDistance(String locationNameFrom, String locationNameTo) {
        double distance = 0;
        return distance;
    }
}
