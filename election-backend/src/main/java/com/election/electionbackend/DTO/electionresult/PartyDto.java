package com.election.electionbackend.DTO.electionresult;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for party data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
public class PartyDto {
    private String id;   // Party ID
    private String name; // Party name
}