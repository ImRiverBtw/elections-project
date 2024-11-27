package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for Municipality entity.
 * This interface extends JpaRepository to provide CRUD operations for Municipality entities.
 */
public interface MunicipalityRepository extends JpaRepository<Municipality, String> {
    // JpaRepository provides standard CRUD methods such as save(), findById(), findAll(), etc.
}