package com.election.electionbackend.repositories.forum;

import com.election.electionbackend.models.forum.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends  JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.username = ?1")
    Optional<Account> findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.email = ?1")
    Optional<Account> findByEmail(String email);

}
