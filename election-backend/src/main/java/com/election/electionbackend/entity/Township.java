package com.election.electionbackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Township {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JsonBackReference
    private Constituency constituency;

    @ManyToOne
    @JoinColumn(name = "affiliation_id")
    private Affiliation affiliation;

    @OneToMany(mappedBy = "township")
    @JsonIgnore
    private Set<TownshipCandidate> townshipCandidates = new HashSet<>();

    public Township() {
    }

    public Township(Constituency constituency, String name, Affiliation affiliation) {
        this.constituency = constituency;
        this.name = name;
        this.affiliation =  affiliation;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public String getAffiliationName() {
        return affiliation != null ? affiliation.getName() : null;
    }

    public Set<TownshipCandidate> getTownshipCandidates() {
        return townshipCandidates;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }
}
