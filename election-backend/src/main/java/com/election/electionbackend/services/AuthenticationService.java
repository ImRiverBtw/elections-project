package com.election.electionbackend.services;

import com.election.electionbackend.APIConfig;
import com.election.electionbackend.DTO.LoginRequest;
import com.election.electionbackend.DTO.LoginResponse;
import com.election.electionbackend.DTO.RegisterRequest;
import com.election.electionbackend.Exceptions.UnauthorizedException;
import com.election.electionbackend.Exceptions.UserAlreadyExistsException;
import com.election.electionbackend.models.forum.User;
import com.election.electionbackend.security.JWToken;
import com.election.electionbackend.security.SecureHasher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final APIConfig apiConfig;

    /**
     * Registers/signs-up a new user.
     * @param request the RegisterRequest containing the username, email, and password for the new user
     * @throws UserAlreadyExistsException if a user with the given username or email already exists
     */
    public void register(RegisterRequest request) {
        //Check if a User with the given username already exists
        if (userService.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }

        //Check if a User with the given email already exists
        if (userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        //Generate a salt for the new user
        byte[] salt = SecureHasher.generateSalt();
        //Create a new user and save it to the database
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setSalt(salt);
        newUser.setPassword(request.getPassword());
        userService.save(newUser);
    }

    /**
     * logs-/signs-in an existing user
     * @param request the LoginRequest containing the email and password for an existing user
     * @return a LoginResponse containing the User object and a JWToken string
     * @throws UnauthorizedException if no user with the given email is found or the password does not match the found user
     */
    public LoginResponse login(LoginRequest request) {
        //tries to find an existing user by email
        User user =userService.findByEmail(request.getEmail());

        //checks for valid login information
        if (user == null || !user.verifyPassword(request.getPassword())) {
            throw new UnauthorizedException("Username or password do not match with an existing account");
        }

        // Issue a token for the account
        JWToken jwToken = new JWToken(user.getUsername(), user.getId(), user.getRole());
        String tokenString = jwToken.encode(this.apiConfig.getIssuer(),
                this.apiConfig.getPassphrase(),  this.apiConfig.getTokenDurationOfValidity());
        return new LoginResponse(user, tokenString);
    }

    public void insertDummyData() {
        String[] usernames = {"john_doe", "jane_doe", "alice_smith", "bob_jones"};
        String[] emails = {"john@example.com", "DaveRobinKayHakanMichael@outlook.com", "jane@example.com", "alice@example.com", "bob@example.com"};
        String[] passwords = {"password123", "password456", "password789", "password101"};

        for (int i = 0; i < usernames.length; i++) {
            RegisterRequest newUser = new RegisterRequest();

            newUser.setUsername(usernames[i]);
            newUser.setEmail(emails[i]);
            newUser.setPassword(passwords[i]);
            try {
                register(newUser);
            } catch (UserAlreadyExistsException e) {
                System.err.println(e.getMessage());
            }
        }
    }

}

