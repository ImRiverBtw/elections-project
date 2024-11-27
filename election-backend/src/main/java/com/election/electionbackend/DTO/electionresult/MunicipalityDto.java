package com.election.electionbackend.DTO.electionresult;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for municipality data.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
public class MunicipalityDto {
    private String id;   // Municipality ID
    private String name; // Municipality name
}