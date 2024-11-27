package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for aggregated vote data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedVoteDto {
    private int id; // ID of the party
    private String name; // Name of the party
    private int votes; // Total votes received by the party
    private int seatCount; // Number of seats allocated to the party
    private double votePercentage; // Percentage of total votes received by the party
}