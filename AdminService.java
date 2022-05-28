package com.example.Phase2.api;

import com.example.Phase2.api.LogInAPI;
import com.example.Phase2.Service.UserManager;
import com.example.Phase2.model.AcceptDriverRequest;
import com.example.Phase2.model.Admin;
import com.example.Phase2.model.Driver;
import com.example.Phase2.model.User;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequestMapping("api/driverService")
@RestController

public class AdminService {

    @PostMapping("AcceptDriverRequest")
    public String AcceptDriver(@RequestBody AcceptDriverRequest newRequest){
        try {
            int driverId=newRequest.getDriverId();
            User targetAccount = UserManager.search(driverId);
            targetAccount.setStatus("Accepted");
            Driver driver = (Driver) targetAccount;
            driver.setAdmin((Admin) UserManager.search(newRequest.getAdminId()));
            UserManager.updateUser(targetAccount,driverId);
            return "Succuss Accept the Pandding Driver";
        } catch (Exception error) {
            return error.getMessage();
        }
    }
    @GetMapping ("showAllDriverPandding")
    public ArrayList<User> ShowAllDriverPandding(){
        ArrayList<User> Users = UserManager.getSystemUsers();
        ArrayList<User>driverPandding=new ArrayList<>();
        for(int i=0;i<Users.size();i++) {
            User user = Users.get(i);
            if (user instanceof Driver && "pandding".equals(user.getStatus())) {
                driverPandding.add(user);
            }
        }
        return driverPandding;
    }
}
