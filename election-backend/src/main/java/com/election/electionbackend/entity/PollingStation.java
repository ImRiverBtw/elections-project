package com.election.electionbackend.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class PollingStation {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String location;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    @OneToMany(mappedBy = "pollingStation")
    private Set<PollingStationCandidate> pollingStationCandidates = new HashSet<>(); //join entity for many to many relation between pollingstation and candidate

    public PollingStation() {
    }

    public PollingStation(Constituency constituency, String name) {
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

    public Set<PollingStationCandidate> getPollingStationCandidates() {
        return pollingStationCandidates;
    }

    public void addPollingStation_Candidate(PollingStationCandidate pollingStationCandidate) {
        pollingStationCandidates.add(pollingStationCandidate);
    }

    public void removePollingStation_Candidate(PollingStationCandidate pollingStationCandidate) {
        pollingStationCandidates.remove(pollingStationCandidate);
    }
}
