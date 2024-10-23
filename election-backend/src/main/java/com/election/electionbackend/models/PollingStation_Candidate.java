package com.election.electionbackend.models;

import jakarta.persistence.*;

@Entity
public class PollingStation_Candidate {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "polling_station_id")
    private PollingStation pollingStation;

    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private int votes;

    public PollingStation_Candidate() {}

    public int getVotes() {
        return votes;
    }
    public void setVotes(int votes) {
        this.votes = votes;
    }

    public PollingStation getPollingStation() {
        return pollingStation;
    }
    public void setPollingStation(PollingStation pollingStation) {
        this.pollingStation = pollingStation;
    }

    public Candidate getCandidate() {
        return candidate;
    }
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public long getId() {
        return id;
    }
}
