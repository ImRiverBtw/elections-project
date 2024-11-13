package com.election.electionbackend.id;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TownshipCandidateId implements Serializable {
    private Long townshipId;
    private CandidateId candidateId;

    public TownshipCandidateId() {
    }

    public TownshipCandidateId(Long townshipId, CandidateId candidateId) {
        this.townshipId = townshipId;
        this.candidateId = candidateId;
    }

    public Long getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(Long townshipId) {
        this.townshipId = townshipId;
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
        TownshipCandidateId that = (TownshipCandidateId) o;
        return Objects.equals(townshipId, that.townshipId) &&
                Objects.equals(candidateId, that.candidateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(townshipId, candidateId);
    }
}
