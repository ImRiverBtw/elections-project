package com.election.electionbackend.controllers;

import com.election.electionbackend.entity.Users;
import com.election.electionbackend.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserController {

    @Autowired
    private UserRepository userRepo;

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
