package com.election.electionbackend.controllers;

import com.election.electionbackend.entity.Users;
import com.election.electionbackend.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/userdata")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // Registeren voor nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Users user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Deze gebruikersnaam is al in gebruik.")
            );
        }

        if (userRepo.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Dit e-mailadres is al in gebruik.")
            );
        }

        userRepo.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }


    //Login van een gebruiker
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Users users) {
        Users existingUser = userRepo.findByEmail(users.getEmail());

        if (existingUser != null && existingUser.getPassword().equals(users.getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "userId", existingUser.getUserId(),
                    "username", existingUser.getUsername()
            ));
        }

        return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
    }


    //Dummy data
    @RequestMapping("/insertDummyData")
    public String insertDummyData() {
        userRepo.insertDummyData();
        return "Dummy data inserted succesfully";
    }

    //TODO moet aan het einde verwijderd worden (is nu om te checken).
    @GetMapping("/getAllUsers")
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }
}
