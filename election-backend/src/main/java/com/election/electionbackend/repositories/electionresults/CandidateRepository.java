package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for Candidate entity.
 * This interface extends JpaRepository to provide CRUD operations for Candidate entities.
 */
public interface CandidateRepository extends JpaRepository<Candidate, String> {

    /**
     * Finds candidates by the party ID.
     *
     * @param partyId the ID of the party
     * @return a list of candidates belonging to the specified party
     */
    List<Candidate> findByParty_Id(String partyId);
}