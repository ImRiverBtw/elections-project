package com.election.electionbackend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Candidate {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="affiliation_id")
    private Affiliation affiliation;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollingStation_Candidate> pollingStation_Candidates = new ArrayList<>();

    public Candidate() {

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
    public List<PollingStation_Candidate> getPollingStation_Candidates() {
        return pollingStation_Candidates;
    }

    public long getId() {
        return id;
    }
}
