package com.election.electionbackend.services;

import com.election.electionbackend.models.forum.User;
import com.election.electionbackend.repositories.forum.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
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


}
