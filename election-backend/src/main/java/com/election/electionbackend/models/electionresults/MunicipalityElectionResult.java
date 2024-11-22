package com.election.electionbackend.models.electionresults;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class MunicipalityElectionResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String municipality;
    private String party;
    private int votes;
    private double percentage;

    // No-args constructor for JPA
    public MunicipalityElectionResult() {}

    // Custom constructor for initialization
    public MunicipalityElectionResult(String municipality, String party, int votes, double percentage) {
        this.municipality = municipality;
        this.party = party;
        this.votes = votes;
        this.percentage = percentage;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
