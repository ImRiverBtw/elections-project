package com.election.electionbackend.entity;

import com.election.electionbackend.id.CandidateId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Candidate {

    @EmbeddedId
    private CandidateId id;

    private String name;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "affiliation_id", referencedColumnName ="affiliation_id")
    @MapsId("affiliationId")
    private Affiliation affiliation;

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private Set<TownshipCandidate> townshipCandidates = new HashSet<>();

    public Candidate() {
    }

    public Candidate(Long candidateNumber, String name, Affiliation affiliation) {
        this.id = new CandidateId(affiliation.getId(), candidateNumber);
        this.name = name;
        this.affiliation = affiliation;

    }

    public CandidateId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    public Set<TownshipCandidate> getTownshipCandidates() {
        return townshipCandidates;
    }

    public void addTownship_Candidate(TownshipCandidate townshipCandidate) {
        townshipCandidates.add(townshipCandidate);
    }

    public void removeTownship_Candidate(TownshipCandidate townshipCandidate) {
        townshipCandidates.remove(townshipCandidate);
    }

}
