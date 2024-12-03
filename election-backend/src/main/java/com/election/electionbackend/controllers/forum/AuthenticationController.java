package com.election.electionbackend.controllers.forum;


import com.election.electionbackend.DTO.LoginRequest;
import com.election.electionbackend.DTO.RegisterRequest;
import com.election.electionbackend.Exceptions.UnauthorizedException;
import com.election.electionbackend.Exceptions.UserAlreadyExistsException;
import com.election.electionbackend.models.forum.User;
import com.election.electionbackend.services.AuthenticationService;
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
    private final AuthenticationService authenticationService;

    // Register a new User
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest request) {
        try {
            authenticationService.register(request);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    //Login a existing user
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest request) {
        try {
            User existingUser = authenticationService.login(request);
            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "userId", existingUser.getId(),
                    "username", existingUser.getUsername()
            ));
        } catch (UnauthorizedException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
