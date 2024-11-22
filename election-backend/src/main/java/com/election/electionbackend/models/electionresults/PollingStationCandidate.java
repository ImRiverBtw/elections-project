package com.election.electionbackend.models.electionresults;

import com.election.electionbackend.id.PollingStationCandidateId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class PollingStationCandidate {
    @EmbeddedId
    private PollingStationCandidateId pollingStationCandidateId;

    @ManyToOne
    @JsonIgnore
    @MapsId("pollingStationId")
    @JoinColumn(name = "polling_station_id", referencedColumnName = "id")
    private PollingStation pollingStation;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumns({
            @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id"),
            @JoinColumn(name = "candidate_number", referencedColumnName = "candidate_number")
    })
    private Candidate candidate;

    private int votes;

    public PollingStationCandidate() {
    }

    public PollingStationCandidate(PollingStation pollingStation, Candidate candidate) {
        this.pollingStation = pollingStation;
        this.candidate = candidate;
        this.pollingStationCandidateId = new PollingStationCandidateId(pollingStation.getId(), candidate.getId());
    }

    public PollingStationCandidateId getId() {
        return pollingStationCandidateId;
    }

    public void setId(PollingStationCandidateId pollingStationCandidateId) {
        this.pollingStationCandidateId = pollingStationCandidateId;
    }

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
}
