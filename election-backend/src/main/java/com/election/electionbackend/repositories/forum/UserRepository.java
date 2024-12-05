package com.election.electionbackend.repositories.forum;

import com.election.electionbackend.models.forum.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Searches for a user by username
     *
     * @param username The username of a user
     * @return The user to which the given username belongs, if it exists
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Searches for a user by email
     *
     * @param email The email address of a user
     * @return The user to which the given email belongs, if it exists
     */
    Optional<User> findUserByEmail(String email);

    /**
     * checks if a user with the given email exists
     *
     * @param email The email address of a user
     * @return true if a user with the given email address already exists, false if a user with the given email address does not exist
     */
    boolean existsByEmail(String email);

    /**
     * checks if a user with the given username exists
     *
     * @param username The username of a user
     * @return true if a user with the given username already exists, false if a user with the given username does not exist
     */
    boolean existsByUsername(String username);
}
