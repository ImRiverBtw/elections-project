package com.election.electionbackend.controllers;

import com.election.electionbackend.entity.Users;
import com.election.electionbackend.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userdata")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    // Registeren voor nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users user) {
        //Controllen of email/gebruikersnaam al in de database staan

        //TODO beter maken dat die alleen laat zien wat er fout is.
        if (userRepo.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            return ResponseEntity.badRequest().body("Gebruiksnaam of emailadres is al in gebruik.");
        }
        userRepo.save(user);
        //bevestiging van registeren
        return ResponseEntity.ok("User registered successfully");
    }

    //Login van een gebruiker
    @PostMapping("/login")
    public String loginUser(@RequestBody Users users) {
        //gebruiker gegevens zoeken om email.
        Users existingUser = userRepo.findByEmail(users.getEmail());

        //gegevens controlleren
        if(existingUser != null && existingUser.getPassword().equals(users.getPassword())) {
            return "Login succesful";
        }

        //error message
        return "Invalid email or password";
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
