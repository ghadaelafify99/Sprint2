package com.example.Phase2.api;
import com.example.Phase2.Service.UserManager;
import com.example.Phase2.model.Admin;
import com.example.Phase2.model.Driver;
import com.example.Phase2.model.Passenger;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/auth")
@RestController

public class UserAuthentication {

        @PostMapping ("UserRegister")
        public String AddNewPassengerApi(@RequestBody Passenger passenger){
            boolean added= UserManager.newRegister(passenger);
            if (added){
                return "passenger added successfully ,User ID "+passenger.getUserId();
            }
            else {
                return "Error: can't add this passenger.";
            }
        }

        @PostMapping("DriverRegister")
        public String AddNewDriverAPI(@RequestBody Driver driver){
                //Add new Driver using user manager service
                boolean added= UserManager.newRegister(driver);
                if (added){
                    return "Driver added successfully , Driver Id: "+driver.getUserId();
                }
                else {
                    return "Error: can't add this Driver.";
                }


        }

        @PostMapping("AddNewAdminAPI")
        public String AddNewAdminAPI(@RequestBody Admin admin){
            //Add new admin using user manager service
            admin.setStatus("admin");
            boolean added=UserManager.addNewAdmin(admin);

            if (added){
                return "Admin added successfully, with admin account id: "+admin.getUserId();
            }
            else {
                return "Error: can't add this admin.";
            }


        }

}
