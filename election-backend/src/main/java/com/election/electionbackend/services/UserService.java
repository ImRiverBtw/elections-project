package com.election.electionbackend.services;

import com.election.electionbackend.Exceptions.ResourceNotFound;
import com.election.electionbackend.models.forum.User;
import com.election.electionbackend.repositories.forum.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User with id: " + id + " not found!"));
    }

    public User findByEmail(String email){
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User with email: " + email + " not found!"));
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void insertDummyData() {
        String[] usernames = {"john_doe", "jane_doe", "alice_smith", "bob_jones"};
        String[] emails = {"john@example.com", "DaveRobinKayHakanMichael@outlook.com", "jane@example.com", "alice@example.com", "bob@example.com"};
        String[] passwords = {"password123", "password456", "password789", "password101"};

        for (int i = 0; i < usernames.length; i++) {
            User user = new User();
            user.setUsername(usernames[i]);
            user.setEmail(emails[i]);
            user.setPassword(passwords[i]);
            userRepository.save(user);
        }
    }
}
