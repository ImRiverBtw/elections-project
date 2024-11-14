package com.election.electionbackend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Township {
    @Id
    @GeneratedValue
    private Long id;
    private String PollingStation;
    private String location;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    @OneToMany(mappedBy = "township")
    private Set<TownshipCandidate> townshipCandidates = new HashSet<>();

    public Township() {
    }

    public Township(Constituency constituency, String name, String townshipNames) {
        this.constituency = constituency;
        this.PollingStation = name;
        this.location = townshipNames;
    }

    public Long getId() {
        return id;
    }

    public String getPollingStation() {
        return PollingStation;
    }

    public void setPollingStation(String PollingStation) {
        this.PollingStation = PollingStation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
