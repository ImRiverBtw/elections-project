package com.election.electionbackend.models.electionresults;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Entity class representing a municipality.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Municipality {

    @Id
    private String id; // Unique identifier for the municipality (e.g., 0755)

    private String name; // Name of the municipality (e.g., Boekel)

}