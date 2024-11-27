package com.election.electionbackend.models.electionresults;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity class representing a candidate in the election.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Candidate {

    @Id
    private String id; // Unique identifier for the candidate

    private String name; // Name of the candidate

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party; // Many-to-one relationship with the Party entity
}