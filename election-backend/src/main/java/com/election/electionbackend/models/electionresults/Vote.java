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
    @JoinColumns({
            @JoinColumn(name = "candidate_identifier", referencedColumnName = "candidate_identifier"),
            @JoinColumn(name = "party_identifier", referencedColumnName = "party_identifier")
    })
    private Candidate candidate; // Relationship with Candidate

    private int validVotes; // Number of valid votes
}
