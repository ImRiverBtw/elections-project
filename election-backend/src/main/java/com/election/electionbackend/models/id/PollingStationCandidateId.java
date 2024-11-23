package com.election.electionbackend.models.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PollingStationCandidateId implements Serializable { //composite id for pollingstationCandidate
    private Long pollingStationId;
    private CandidateId candidateId;

    public PollingStationCandidateId() {
    }

    public PollingStationCandidateId(Long pollingStationId, CandidateId candidateId) {
        this.pollingStationId = pollingStationId;
        this.candidateId = candidateId;
    }

    public Long getPollingStationId() {
        return pollingStationId;
    }

    public void setPollingStationId(Long pollingStationId) {
        this.pollingStationId = pollingStationId;
    }

    public CandidateId getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(CandidateId candidateId) {
        this.candidateId = candidateId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PollingStationCandidateId that = (PollingStationCandidateId) o;
        return Objects.equals(pollingStationId, that.pollingStationId) &&
                Objects.equals(candidateId, that.candidateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pollingStationId, candidateId);
    }
}
