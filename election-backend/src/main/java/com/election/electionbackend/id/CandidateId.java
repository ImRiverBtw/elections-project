package com.election.electionbackend.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CandidateId implements Serializable {
    @Column(name="affiliation_id")
    private Long affiliationId;

    @Column(name = "candidate_number")
    private Long candidateNumber;

    public CandidateId() {
    }

    public CandidateId(Long affiliationId, Long candidateNumber) {
        this.affiliationId = affiliationId;
        this.candidateNumber = candidateNumber;
    }

    public Long getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(Long affiliationId) {
        this.affiliationId = affiliationId;
    }

    public Long getCandidateNumber() {
        return candidateNumber;
    }

    public void setCandidateNumber(Long candidateNumber) {
        this.candidateNumber = candidateNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CandidateId that = (CandidateId) o;
        return Objects.equals(affiliationId, that.affiliationId) && Objects.equals(candidateNumber, that.candidateNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(affiliationId, candidateNumber);
    }
}
