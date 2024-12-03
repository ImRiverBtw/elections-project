package com.election.electionbackend.services;

import com.election.electionbackend.DTO.LoginRequest;
import com.election.electionbackend.DTO.RegisterRequest;
import com.election.electionbackend.Exceptions.UnauthorizedException;
import com.election.electionbackend.Exceptions.UserAlreadyExistsException;
import com.election.electionbackend.models.forum.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;

    public void register(RegisterRequest request) {
        //Check if a User with the given username already exists
        if (userService.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + request.getUsername());
        }

        //Check if a User with the given email already exists
        if (userService.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already exists: " + request.getEmail());
        }

        //Create a new user and save it to the database
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        System.out.println(newUser.getPassword());
        userService.save(newUser);
    }

    public User login(LoginRequest request) {
        User user = userService.findByEmail(request.getEmail());
        if (user == null || !user.verifyPassword(request.getPassword())) {
            throw new UnauthorizedException("Username or password do not match with an existing account");
        }
        return user;
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

