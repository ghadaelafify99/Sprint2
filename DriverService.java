package com.example.Phase2.api;

import com.example.Phase2.Service.RideManager;
import com.example.Phase2.Service.UserManager;
import com.example.Phase2.model.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RequestMapping("api/DriverService")
@RestController

public class DriverService {


    @PostMapping("AddfavouriteArea")
    String AddfavouriteArea(@RequestBody AddNewArea newArea){
        try {
            Driver driver = (Driver) UserManager.search(newArea.getDriverId());
            driver.addFavouriteArea(newArea.getArea());
            UserManager.updateUser(driver, newArea.getDriverId());
            return "Success Add new Area";
        } catch (Exception ex) {
            return "No exist that account";
        }



    }


    @PostMapping ("SelectRide")
    String SelectRide(@RequestBody DriverSelectRide object){
        try {
            Driver driver = (Driver) UserManager.search(object.getDriverId());
            driver.setcurrentRideId(object.getRideId());
            UserManager.updateUser(driver, object.getDriverId());
            Ride selectedRide = RideManager.searchById(object.getRideId());
            selectedRide.addToMap(object.getDriverId(), object.getPrice());//in class Ride
            RideManager.updateRide(selectedRide, object.getRideId());
            return "Sucesss Select Ride";
        }catch(Exception error){return error.getMessage();}
    }

    @PostMapping("rideNotification")
    String rideNotification(@RequestBody DriverID obj){
        try {
            Driver driver = (Driver) UserManager.search(obj.getDriverId());

            if (driver.getCurrentRideId() != -1) {
                Ride ride = RideManager.searchById(driver.getCurrentRideId());

                if (driver.getUserId() == ride.getDriver().getUserId()) {

                    return "The Passenger accpted your offer:"+
                            "passenger MobilePhone: " + ride.getpassenger().getMobileNum()
                            + "  " + "Passenger Name :  " + ride.getpassenger().getUserName();
                } else {

                    driver.setcurrentRideId(-1);
                    UserManager.updateUser(driver,obj.getDriverId());
                    return ("THe Passenger not accepted the offer");
                }
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }
        return null;
    }
    @PostMapping ("GetAllRidesFromMySource")
    ArrayList<Ride> GetAllRidesFromMySource(@RequestBody DriverID driverID) {
        try {
            Driver driver = (Driver) UserManager.search(driverID.getDriverId());
            ArrayList<Ride> commonArea=null;
            if (driver.getCurrentRideId() == -1) {
                ArrayList<String> favoriteArea = driver.getfavouruteArea();
                commonArea = RideManager.searchRide(favoriteArea);
            }
            return commonArea;
        }
        catch(Exception error){
            return null;
        }
    }


}
