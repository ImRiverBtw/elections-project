package com.election.electionbackend.models.electionresults;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity class representing a vote.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique identifier for the vote

    @ManyToOne
    @JoinColumn(name = "polling_station_id")
    private PollingStation pollingStation; // Many-to-one relationship with the PollingStation entity

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party; // Many-to-one relationship with the Party entity

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = true)
    private Candidate candidate; // Many-to-one relationship with the Candidate entity (nullable)

    private int validVotes; // Number of valid votes
}