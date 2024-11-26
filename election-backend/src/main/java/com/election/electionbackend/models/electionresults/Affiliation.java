package com.election.electionbackend.models.electionresults;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Affiliation {

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, name="affiliation_id")
    private Long affiliationId;
    private String name;
    private Integer seatCount;
    private Integer Votes;

    @OneToMany(mappedBy = "affiliation", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Candidate> candidates = new ArrayList<>();

    public Affiliation() {
    }

    public Affiliation(String name) {
        this.name = name;
    }

    public Long getId() {
        return affiliationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void removeCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }

    public Integer getSeatCount() {
        return seatCount;
    }
    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
    public Integer getVotes() {
        return Votes;
    }
    public void setVotes(Integer votes) {
        Votes = votes;
    }
}
