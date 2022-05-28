package com.example.Phase2.api;

import com.example.Phase2.Service.RideManager;
import com.example.Phase2.Service.UserManager;
import com.example.Phase2.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("api/PassengerService")
@RestController

public class PassengerService {
    @PostMapping("RequestRide")
    public String RequestRide(@RequestBody RequestRide newRide) {
        try {
            Passenger passenger = (Passenger) UserManager.search(newRide.getPassengerId());
            if (passenger.getcurrentRideId() != -1) {
                return "Sorry you can not register new ride before end the current ride";
            } else {
                Object ride = passenger.rideRequest(newRide.getSource(), newRide.getDestination());
                int rideId = RideManager.addNewRide(ride);
                passenger.setcurrentRideId(rideId);
                UserManager.updateUser(passenger, newRide.getPassengerId());

            }
            return "Success Ride Register";
        } catch (Exception error) {
            return error.getMessage();
        }

    }

    @PostMapping  ("showAllRideOffers")
    public Map<Integer,String> showAllRideOffers(@RequestBody PassengerId obj) {
        try {
            Passenger passenger = (Passenger) UserManager.search(obj.getPassengerId());
            int rideId = passenger.getcurrentRideId();

            if (rideId != -1) {
                Ride currentRide =RideManager.search(rideId,obj.getPassengerId());

                if (!currentRide.getRideoffers().isEmpty()) {

                    if (("Start".equals(currentRide.getStatus()))) {
                        return null;
                    } else {
                            return currentRide.getRideoffers();
                    }
                }
                //return null;
            }}catch(Exception error){}
    return null;
    }

    @PostMapping  ("SelectDriverOffer")
    public String SelectDriverOffer(@RequestBody RideSelection obj){
        try {

                  Passenger passenger = (Passenger) UserManager.search(obj.getPassengerId());
                 int rideId = passenger.getcurrentRideId();
                 Ride currentRide =RideManager.search(rideId,obj.getPassengerId());
                currentRide.setDriver((Driver)UserManager.search(obj.getDriverId()));
                currentRide.setStatus("Start");
                RideManager.updateRide(currentRide, rideId);
                return "Success Select";
        } catch (Exception error) {
           return "The Info not correct try Again";
        }
    }

}
