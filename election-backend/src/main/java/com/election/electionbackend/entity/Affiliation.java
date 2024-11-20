package com.election.electionbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Affiliation {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name="affiliation_id")
    private Long affiliationId;
    private String name;

    @OneToMany(mappedBy = "affiliation", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Candidate> candidates = new ArrayList<>();

    public Affiliation() {
    }

    public Affiliation(String name) {
        this.name = name;
    }

    public Long getId() {
        return affiliationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }
}
