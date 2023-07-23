package com.example.Expense.API.controller;

import com.example.Expense.API.model.Expense;
import com.example.Expense.API.model.User;
import com.example.Expense.API.model.dto.SignInInput;
import com.example.Expense.API.model.dto.SignUpOutput;
import com.example.Expense.API.service.AuthenticationService;
import com.example.Expense.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("user/signup")
    public SignUpOutput signUpUser(@RequestBody User user){
        return userService.signUpUser(user);
    }

    @PostMapping("user/signin")
    public String signInUser(@RequestBody @Valid SignInInput signInInput){
        return userService.signInUser(signInInput);
    }

    @PostMapping("expense")
    public String createExpenses(@RequestBody Expense expense, @RequestParam String email, @RequestParam String token){
        if(authenticationService.authenticate(email,token)){
            return userService.createExpenses(expense,email);
        }else{
            return "Not an Authenticator user activity!!!";
        }
    }

    @DeleteMapping("expense")
    public String removeExpenses(@RequestParam Long id, @RequestParam String email, @RequestParam String token){
        if(authenticationService.authenticate(email,token)){
            return userService.removeExpenses(id,email);
        }else{
            return "Not an Authenticated user activity!!!";
        }
    }

    @GetMapping("users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }


}
