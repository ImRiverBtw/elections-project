package com.election.electionbackend.DTO.electionresult;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for vote data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
public class VoteDto {
    private String pollingStationId; // Polling station ID where the vote was cast
    private String partyId;          // Party ID for which the vote was cast
    private String candidateId;      // (Optional) Candidate ID for which the vote was cast, if applicable
    private int validVotes;          // Number of valid votes
}