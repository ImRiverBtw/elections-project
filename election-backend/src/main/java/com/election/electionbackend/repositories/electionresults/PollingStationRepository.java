package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.PollingStation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for PollingStation entity.
 * This interface extends JpaRepository to provide CRUD operations for PollingStation entities.
 */
public interface PollingStationRepository extends JpaRepository<PollingStation, String> {

    /**
     * Finds polling stations by the municipality ID.
     *
     * @param municipalityId the ID of the municipality
     * @return a list of polling stations belonging to the specified municipality
     */
    List<PollingStation> findByMunicipality_Id(String municipalityId);
}