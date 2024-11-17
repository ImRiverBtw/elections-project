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

    @ManyToOne
    @JoinColumn(name = "affiliation_id")
    private Affiliation affiliation;

    @OneToMany(mappedBy = "township")
    private Set<TownshipCandidate> townshipCandidates = new HashSet<>();

    public Township() {
    }

    public Township(Constituency constituency, String name, Affiliation affiliation) {
        this.constituency = constituency;
        this.name = name;
        this.affiliation =  affiliation;
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


    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }
}
