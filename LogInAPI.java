package com.example.Phase2.api;

import com.example.Phase2.Service.UserManager;
import com.example.Phase2.model.User;
import com.example.Phase2.model.UserLogIn;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/LogIn")
@RestController

public class LogInAPI {
    @PostMapping ("accountLogIN")
    public String LogIn(@RequestBody UserLogIn user){
        try {
            User target = UserManager.searchForLogIn(user.getUserName(), user.getPassword());
            return "Welcome Sucesss LogIn, UserId "+target.getUserId();
        }catch(Exception error){
            return error.getMessage();
        }
    }
}
