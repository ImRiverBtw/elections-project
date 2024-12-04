package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAggregatedVoteDto {
    private long id; // ID of the party
    private String name; // Name of the party
    private long votes; // Total votes received by the party
    private double votePercentage; // Percentage of total votes received by the party

}
