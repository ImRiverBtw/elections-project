package com.election.electionbackend.controllers;


import com.election.electionbackend.DTO.LoginRequest;
import com.election.electionbackend.DTO.RegisterRequest;
import com.election.electionbackend.models.forum.Account;
import com.election.electionbackend.models.forum.Users;
import com.election.electionbackend.security.SecureHasher;
import com.election.electionbackend.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AccountService accountService;

    // Register a new Account
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {

        //Check if an account with the given displayName already exists
        if (accountService.existsByDisplayName(registerRequest.displayName())){
            return ResponseEntity.badRequest().body("Gebruiksnaam is al in gebruik.");
        }

        //Check if an account with the given email already exists
        if (accountService.existsByEmail(registerRequest.email())){
            return ResponseEntity.badRequest().body("Emailadres is al in gebruik.");
        }

        //save the account if none exist with the given email and displayName
        accountService.save(new Account(registerRequest.displayName(), registerRequest.email(), registerRequest.password()));
        return ResponseEntity.ok("User registered successfully");
    }

    //Login van een gebruiker
    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest loginRequest) {
        //gebruiker gegevens zoeken om email.
        Account existingAccount = accountService.findByEmail(loginRequest.email());

        //gegevens controlleren
        if(existingAccount != null && existingAccount.getPassword().equals(SecureHasher.secureHash(loginRequest.password()))) {
            return "Login succesful";
        }

        //error message
        return "Invalid email or password";
    }
}
