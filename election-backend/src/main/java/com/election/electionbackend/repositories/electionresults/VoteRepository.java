package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.DTO.electionresult.AffiliationDto;
import com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto;
import com.election.electionbackend.models.electionresults.Vote;
import com.election.electionbackend.models.id.CandidateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Vote entity.
 * This interface extends JpaRepository to provide CRUD operations for Vote entities.
 */
@Repository
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
    List<Vote> findByCandidate_Id(CandidateId candidateId);

    /**
     * Finds votes for a party within a specific polling station.
     *
     * @param pollingStationId the ID of the polling station
     * @param partyId          the ID of the party
     * @return a list of votes for the specified party within the specified polling station
     */
    List<Vote> findByPollingStation_IdAndParty_Id(String pollingStationId, String partyId);

    /**
     * Finds votes for a candidate within a specific polling station.
     *
     * @param pollingStationId the ID of the polling station
     * @param candidateId      the ID of the candidate
     * @return a list of votes for the specified candidate within the specified polling station
     */
    List<Vote> findByPollingStation_IdAndCandidate_Id(String pollingStationId, CandidateId candidateId);

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


    /**
     * Retrieves the total number of valid votes (i.e., votes that have a candidate).
     * @return The total number of valid votes as a long.
     */
    @Query("SELECT SUM(v.validVotes) FROM Vote v WHERE v.candidate IS NOT NULL")
    long getTotalValidVotes();

    /**
     * Retrieves the total number of valid votes in a specific municipality, used for calculating percentages
     * @param municipalityId  The id of a municipality
     * @return The total number of votes as a long.
     */
    @Query("SELECT SUM(v.validVotes) FROM Vote v WHERE v.candidate IS NOT NULL AND v.pollingStation.municipality.id = :municipalityId")
    long getTotalValidVotesForMunicipality(@Param("municipalityId") String municipalityId);

    /**
     * Retrieves the total number of valid votes in a specific pollingStation, used for calculating percentages
     * @param pollingStationId  The id of a municipality
     * @return The total number of votes as a long.
     */
    @Query("SELECT SUM(v.validVotes) FROM Vote v WHERE v.candidate IS NOT NULL AND v.pollingStation.id = :pollingStationId")
    long getTotalValidVotesForPollingStation(@Param("pollingStationId") String pollingStationId);

    /**
     * Retrieves the total votes for a specific party
     * @param partyId The id of a party
     * @return An integer representing the total amount of votes a party has
     */
    @Query("SELECT SUM(v.validVotes) FROM Vote v WHERE v.party.id = :partyId AND v.candidate IS NOT NULL")
    int findTotalVotesForParty(@Param("partyId") String partyId);

    /**
     * Retrieves the total votes for a specific party
     * @param candidateId The id of candidate
     * @return An integer representing the total amount of votes a party has
     */
    @Query("SELECT SUM(v.validVotes) FROM Vote v WHERE v.candidate.id = :candidateId")
    int findTotalVotesForCandidate(@Param("candidateId") CandidateId candidateId);

    /**
     * Retrieves the total votes grouped by party and calculates the vote percentage for each party.
     * @param totalValidVotes The total number of valid votes to calculate the percentage.
     * @return A list of NewAggregatedVoteDto containing party id, name, total votes, and vote percentage.
     */
    @Query("SELECT new com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto(" + //new DTO
            "CAST(p.id AS long), " + //party id
            "p.name, " + //party name
            "SUM(v.validVotes), " + //sum up the votes
            "ROUND((CAST(SUM(v.validVotes) AS double) / :totalValidVotes) * 100, 2))" +  // calculate the percentage and round to 2 decimal places
            "FROM Vote v JOIN v.party p WHERE v.party IS NOT NULL AND v.candidate IS NOT NULL " + //only include values with a valid party and candidate
            "GROUP BY p.id, p.name ORDER BY SUM(v.validVotes) DESC")
    List<NewAggregatedVoteDto> findVotesGroupedByParty(@Param("totalValidVotes") long totalValidVotes);

    /**
     * Retrieves the total votes grouped by party for a given municipality and calculates the vote percentage for each party.
     * @param totalValidVotes The total number of valid votes to calculate the percentage.
     * @param municipalityId The id of a municipality
     * @return A list of NewAggregatedVoteDto containing party id, name, total votes, and vote percentage.
     */
    @Query("SELECT new com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto(" + //new DTO
            "CAST(p.id AS long), " + //party id
            "p.name, " + //party name
            "SUM(v.validVotes), " + //sum up the votes
            "ROUND((CAST(SUM(v.validVotes) AS double) / :totalValidVotes) * 100, 2))" +
            "FROM Vote v JOIN v.party p WHERE v.party IS NOT NULL AND v.candidate IS NOT NULL AND v.pollingStation.municipality.id = :municipalityId " +
            "GROUP BY p.id, p.name ORDER BY SUM(v.validVotes) DESC")
    List<NewAggregatedVoteDto> findVotesGroupedByPartyForMunicipality(@Param("totalValidVotes") long totalValidVotes, @Param("municipalityId") String municipalityId);

    /**
     * Retrieves the total votes grouped by party for a given pollingStation and calculates the vote percentage for each party.
     * @param totalValidVotes The total number of valid votes to calculate the percentage.
     * @param pollingStationId The id of a pollingStation
     * @return A list of NewAggregatedVoteDto containing party id, name, total votes, and vote percentage.
     */
    @Query("SELECT new com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto(" + //new DTO
            "CAST(p.id AS long), " + //party id
            "p.name, " + //party name
            "SUM(v.validVotes), " + //sum up the votes
            "ROUND((CAST(SUM(v.validVotes) AS double) / :totalValidVotes) * 100, 2))" +
            "FROM Vote v JOIN v.party p WHERE v.party IS NOT NULL AND v.candidate IS NOT NULL AND v.pollingStation.id = :pollingStationId " +
            "GROUP BY p.id, p.name ORDER BY SUM(v.validVotes) DESC")
    List<NewAggregatedVoteDto> findVotesGroupedByPartyForPollingStation(@Param("totalValidVotes") long totalValidVotes, @Param("pollingStationId") String pollingStationId);

    /**
     * Retrieves the party with the most votes for a given municipality
     * @param municipalityId The id of a municipality
     * @return A list of AffiliationDto ordered by votes descending
     */
    @Query("SELECT new com.election.electionbackend.DTO.electionresult.AffiliationDto(v.party.id, v.party.name, SUM(v.validVotes)) " +
            "FROM Vote v " +
            "WHERE v.party IS NOT NULL AND v.candidate IS NOT NULL " +
            "AND v.pollingStation.municipality.id = :municipalityId " +
            "GROUP BY v.party.id, v.party.name " +
            "ORDER BY SUM(v.validVotes) DESC")
    List<AffiliationDto> findTopParties(@Param("municipalityId") String municipalityId);
}