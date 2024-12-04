package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for municipality party data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipalityPartyDto {
    private String id; // Municipality party ID
    private String name; // Municipality party name
    private AffiliationDto affiliation; // Affiliation details of the municipality party


}