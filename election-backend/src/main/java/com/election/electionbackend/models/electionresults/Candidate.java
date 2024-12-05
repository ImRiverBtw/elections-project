package com.election.electionbackend.models.electionresults;

import com.election.electionbackend.models.id.CandidateId;
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

    @EmbeddedId
    private CandidateId id; // Embedded ID, which includes party_id

    private String name; // Name of the candidate

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party; // Many-to-one relationship with the Party entity
}
