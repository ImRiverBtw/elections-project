package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for polling station data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PollingStationDto {
    private String id;           // Polling station ID
    private String name;         // Polling station name
    private String municipalityId; // Municipality ID to which this polling station belongs
}