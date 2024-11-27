package com.election.electionbackend.models.electionresults;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Entity class representing a polling station.
 * This class uses Lombok annotations to reduce boilerplate code.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PollingStation {

    @Id
    private String id; // Unique identifier for the polling station (e.g., 0755::SB1)

    private String name; // Name of the polling station (e.g., Stembureau Gemeentehuis)

    @ManyToOne
    @JoinColumn(name = "municipality_id")
    private Municipality municipality; // Many-to-one relationship with the Municipality entity
}