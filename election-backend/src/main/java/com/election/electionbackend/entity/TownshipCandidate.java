package com.election.electionbackend.entity;

import com.election.electionbackend.id.TownshipCandidateId;
import jakarta.persistence.*;

@Entity
public class TownshipCandidate {
    @EmbeddedId
    private TownshipCandidateId townshipCandidateId;

    @ManyToOne
    @MapsId("townshipId")
    @JoinColumn(name = "township_id", referencedColumnName = "id")
    private Township township;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumns({
            @JoinColumn(name = "affiliation_id", referencedColumnName = "affiliation_id"),
            @JoinColumn(name = "candidate_number", referencedColumnName = "candidate_number")
    })
    private Candidate candidate;

    private int votes;

    public TownshipCandidate() {
    }

    public TownshipCandidate(Township township, Candidate candidate) {
        this.township = township;
        this.candidate = candidate;
        this.townshipCandidateId = new TownshipCandidateId(township.getId(), candidate.getId());
    }

    public TownshipCandidateId getId() {
        return townshipCandidateId;
    }

    public void setId(TownshipCandidateId townshipCandidateId) {
        this.townshipCandidateId = townshipCandidateId;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Township getTownship() {
        return township;
    }

    public void setTownship(Township township) {
        this.township = township;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
