package com.election.electionbackend.models;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Affiliation {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "affiliation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates = new ArrayList<>();

    public Affiliation() {

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

    public long getId() {
        return id;
    }
}
