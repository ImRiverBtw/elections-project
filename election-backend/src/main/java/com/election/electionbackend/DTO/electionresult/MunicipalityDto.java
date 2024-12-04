package com.election.electionbackend.DTO.electionresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for municipality data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipalityDto {
    private String id;   // Municipality ID
    private String name; // Municipality name
}