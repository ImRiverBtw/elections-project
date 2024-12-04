package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationDto {
    private String id; // Affiliation ID
    private String name; // Affiliation name
    private long votes; // Total votes received by the affiliation
}
