package com.election.electionbackend.DTO.electionresult;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for candidate data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
public class CandidateDto {
    private String id;      // Candidate ID
    private String partyId; // Party ID to which this candidate belongs
    private String name;    // (Optional) Candidate name, if available
}