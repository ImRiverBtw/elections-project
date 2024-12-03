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

}

