package com.election.electionbackend.controllers.forum;


import com.election.electionbackend.DTO.LoginRequest;
import com.election.electionbackend.DTO.RegisterRequest;
import com.election.electionbackend.models.forum.User;
import com.election.electionbackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    // Register a new User
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest request) {
        //Check if a User with the given username already exists
        System.out.println(request);
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Deze gebruikersnaam is al in gebruik.")
            );
        }
        //Check if a User with the given email already exists
        if (userService.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Dit e-mailadres is al in gebruik.")
            );
        }
        //save the User if none exist with the given email and username
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        System.out.println(newUser.getPassword());
        userService.save(newUser);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    //Login a existing user
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest request) {
        //search for user details by email
        User existingUser = userService.findByEmail(request.getEmail());

        //check if the password is correct
        if (existingUser != null && existingUser.verifyPassword(request.getPassword())) {
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "userId", existingUser.getId(),
                    "username", existingUser.getUsername()
            ));
        }

        return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
    }
}
