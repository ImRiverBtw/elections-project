package com.election.electionbackend.models.electionresults;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity class representing a political party.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Party {

    @Id
    private String id; // Unique identifier for the party (e.g., 1)

    private String name; // Name of the party (e.g., VVD)
}