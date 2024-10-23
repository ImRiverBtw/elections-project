package com.election.electionbackend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PollingStation {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    @OneToMany(mappedBy = "pollingStation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollingStation_Candidate> pollingStation_Candidates = new ArrayList<>();

    public PollingStation() {}

    public Constituency getConstituency() {
        return constituency;
    }
    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }

    public List<PollingStation_Candidate> getPollingStation_Candidates() {
        return pollingStation_Candidates;
    }
}
