package com.election.electionbackend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Township {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    @OneToMany(mappedBy = "township")
    private Set<TownshipCandidate> townshipCandidates = new HashSet<>();

    public Township() {
    }

    public Township(Constituency constituency, String name) {
        this.constituency = constituency;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    public Set<TownshipCandidate> getTownshipCandidates() {
        return townshipCandidates;
    }

    public void addTownship_Candidate(TownshipCandidate townshipCandidate) {
        townshipCandidates.add(townshipCandidate);
    }

    public void removeTownship_Candidate(TownshipCandidate townshipCandidate) {
        townshipCandidates.remove(townshipCandidate);
    }
}
