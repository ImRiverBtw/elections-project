package com.election.electionbackend.models.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CandidateId implements Serializable {

    @Column(name = "candidate_identifier") // Maps to database column candidate_identifier
    private String candidateIdentifier;

    @Column(name = "party_identifier") // Maps to database column party_id
    private String partyIdentifier;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CandidateId that)) return false;
        return Objects.equals(getPartyIdentifier(), that.getPartyIdentifier()) && Objects.equals(getCandidateIdentifier(), that.getCandidateIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPartyIdentifier(), getCandidateIdentifier());
    }
}
