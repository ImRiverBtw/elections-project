package com.election.electionbackend.controllers;

import com.election.electionbackend.entity.PasswordResetToken;
import com.election.electionbackend.entity.Users;
import com.election.electionbackend.jpa.PasswordResetTokenRepository;
import com.election.electionbackend.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/userdata")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordResetTokenRepository tokenRepo;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        Users user = userRepo.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body("User with this email does not exist.");
        }

        // Generate token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        resetToken.setUser(user);

        tokenRepo.save(resetToken);

        // Prepare reset link
        String resetLink = "http://127.0.0.1:5173/reset-password?token=" + token;

        // Email content
        String plainText = "Click the link to reset your password: " + resetLink;
        String htmlText = "<p>Click the link to reset your password:</p>" +
                "<p><a href='" + resetLink + "' target='_blank'>" + resetLink + "</a></p>";

        // Send reset link via Node.js backend
        RestTemplate restTemplate = new RestTemplate();
        String emailBackendUrl = "http://localhost:3000/send-email"; // Node.js email backend URL
        Map<String, String> emailRequest = new HashMap<>();
        emailRequest.put("to", email);
        emailRequest.put("subject", "Password Reset Request");
        emailRequest.put("text", plainText); // Plain text content
        emailRequest.put("html", htmlText); // HTML content

        try {
            restTemplate.postForObject(emailBackendUrl, emailRequest, String.class);
            return ResponseEntity.ok("Password reset email sent.");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email.");
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String newPassword = requestBody.get("newPassword");

        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token is required.");
        }
        if (newPassword == null || newPassword.isEmpty()) {
            return ResponseEntity.badRequest().body("New password is required.");
        }

        Optional<PasswordResetToken> optionalToken = tokenRepo.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        PasswordResetToken resetToken = optionalToken.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Token has expired.");
        }

        Users user = resetToken.getUser();
        if (user == null) {
            return ResponseEntity.badRequest().body("User with this email does not exist.");
        }

        user.setPassword(newPassword); // Ideally, hash the password before saving
        userRepo.save(user);

        tokenRepo.delete(resetToken); // Remove token after successful password reset

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
