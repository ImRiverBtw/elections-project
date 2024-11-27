package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.Party;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Party entity.
 * This interface extends JpaRepository to provide CRUD operations for Party entities.
 */
public interface PartyRepository extends JpaRepository<Party, String> {
    // JpaRepository provides standard CRUD methods such as save(), findById(), findAll(), etc.
}