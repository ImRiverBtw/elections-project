package com.election.electionbackend.controllers;

import com.election.electionbackend.entity.PasswordResetToken;
import com.election.electionbackend.entity.Users;
import com.election.electionbackend.jpa.PasswordResetTokenRepository;
import com.election.electionbackend.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/userdata")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> requestBody) {
        // Extract email from JSON body
        String email = requestBody.get("email");

        // Validate email
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required.");
        }

        // Find user by email
        Users user = userRepo.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found.");
        }

        // Generate reset token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));
        resetToken.setUser(user);

        // Save reset token
        tokenRepo.save(resetToken);

        // Simulate sending email (log the token for now)
        System.out.println("Password reset token: " + token);

        return ResponseEntity.ok("Password reset token has been sent to your email.");
    }

    // Reset password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String newPassword = requestBody.get("newPassword");

        // Validate token and password
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token is required.");
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("New password is required.");
        }

        // Check token validity
        Optional<PasswordResetToken> optionalToken = tokenRepo.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        PasswordResetToken resetToken = optionalToken.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired.");
        }

        // Update user's password
        Users user = resetToken.getUser();
        user.setPassword(newPassword); // Ideally hash the password here
        userRepo.save(user);

        // Delete the used token
        tokenRepo.delete(resetToken);

        return ResponseEntity.ok("Password has been reset successfully.");
    }


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
