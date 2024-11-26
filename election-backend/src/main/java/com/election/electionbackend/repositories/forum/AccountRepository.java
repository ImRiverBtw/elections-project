package com.election.electionbackend.repositories.forum;

import com.election.electionbackend.models.forum.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends  JpaRepository<Account, Long> {

    Optional<Account> findByDisplayName(String username);

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByDisplayName(String displayName);



}
