package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for candidate data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@AllArgsConstructor
public class CandidateDto {
    private String identifier;      // Candidate ID
    private String partyId; // Party ID to which this candidate belongs
    private String name;    // (Optional) Candidate name, if available
    private int votes;
}