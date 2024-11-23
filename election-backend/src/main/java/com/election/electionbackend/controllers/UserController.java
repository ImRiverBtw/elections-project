package com.election.electionbackend.controllers;

import com.election.electionbackend.entity.Users;
import com.election.electionbackend.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/register")
    public String registerUser(@RequestBody Users user) {
        userRepo.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Users users) {
        Users existingUser = userRepo.findByEmail(users.getEmail());
        if(existingUser != null && existingUser.getPassword().equals(users.getPassword())) {
            return "Login succesful";
        }
        return "Invalid email or password";
    }

    @RequestMapping("/insertDummyData")
    public String insertDummyData() {
        userRepo.insertDummyData();
        return "Dummy data inserted succesfully";
    }

    @GetMapping("/getAllUsers")
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }
}
