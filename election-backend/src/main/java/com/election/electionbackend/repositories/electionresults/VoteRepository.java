package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for Vote entity.
 * This interface extends JpaRepository to provide CRUD operations for Vote entities.
 */
public interface VoteRepository extends JpaRepository<Vote, Long> {

    /**
     * Finds votes by the polling station ID.
     *
     * @param pollingStationId the ID of the polling station
     * @return a list of votes belonging to the specified polling station
     */
    List<Vote> findByPollingStation_Id(String pollingStationId);

    /**
     * Finds votes by the party ID.
     *
     * @param partyId the ID of the party
     * @return a list of votes belonging to the specified party
     */
    List<Vote> findByParty_Id(String partyId);

    /**
     * Finds votes by the candidate ID.
     *
     * @param candidateId the ID of the candidate
     * @return a list of votes belonging to the specified candidate
     */
    List<Vote> findByCandidate_Id(String candidateId);

    /**
     * Finds votes for a party within a specific polling station.
     *
     * @param pollingStationId the ID of the polling station
     * @param partyId the ID of the party
     * @return a list of votes for the specified party within the specified polling station
     */
    List<Vote> findByPollingStation_IdAndParty_Id(String pollingStationId, String partyId);

    /**
     * Finds votes for a candidate within a specific polling station.
     *
     * @param pollingStationId the ID of the polling station
     * @param candidateId the ID of the candidate
     * @return a list of votes for the specified candidate within the specified polling station
     */
    List<Vote> findByPollingStation_IdAndCandidate_Id(String pollingStationId, String candidateId);

    /**
     * Finds votes by the municipality ID of the polling station.
     *
     * @param municipalityId the ID of the municipality
     * @return a list of votes belonging to the specified municipality
     */
    List<Vote> findByPollingStation_Municipality_Id(String municipalityId);

    /**
     * Counts the number of votes for a specific party.
     *
     * @param partyId the ID of the party
     * @return the number of votes for the specified party
     */
    long countByParty_Id(String partyId);
}