package com.election.electionbackend.controllers.forum;


import com.election.electionbackend.DTO.LoginRequest;
import com.election.electionbackend.DTO.LoginResponse;
import com.election.electionbackend.DTO.RegisterRequest;
import com.election.electionbackend.Exceptions.UnauthorizedException;
import com.election.electionbackend.Exceptions.UserAlreadyExistsException;
import com.election.electionbackend.models.forum.User;
import com.election.electionbackend.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    //Endpoint to register a new User
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest request) {
        try {
            //tries to register the user
            authenticationService.register(request);
            return ResponseEntity.ok(Map.of("message", "User registered successfully"));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    //Endpoint to log in an existing User
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest request) {
        try {
            //tries to log in the user
            LoginResponse loginResponse = authenticationService.login(request);
            //get the User object and JWToken from the loginResponse
            User user = loginResponse.getUser();
            String tokenString = loginResponse.getTokenString();

            //returns a response-entity with the new jwtoken
            return ResponseEntity.accepted()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                    .body(user);
        } catch (UnauthorizedException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
        }
    }
}
