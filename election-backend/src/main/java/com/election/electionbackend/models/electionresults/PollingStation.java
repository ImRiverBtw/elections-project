package com.election.electionbackend.models.electionresults;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class PollingStation {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JsonBackReference
    private Constituency constituency;

    @ManyToOne
    @JoinColumn(name = "affiliation_id")
    private Affiliation affiliation;


    @OneToMany(mappedBy = "pollingStation")
    private Set<PollingStationCandidate> pollingStationCandidates = new HashSet<>(); //join entity for many to many relation between pollingstation and candidate

    public PollingStation() {
    }

    public PollingStation(Constituency constituency, String name, Affiliation affiliation) {
        this.constituency = constituency;
        this.name = name;
        this.affiliation = affiliation;
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

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public String getAffiliationName() {
        return affiliation != null ? affiliation.getName() : null;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
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
