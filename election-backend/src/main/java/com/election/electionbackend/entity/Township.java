package com.election.electionbackend.entity;

import com.election.electionbackend.jpa.AffiliationRepository;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Township {
    @Id
    @GeneratedValue
    private Long id;

    private String location;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    @OneToMany(mappedBy = "township", cascade = CascadeType.ALL)
    private Set<TownshipCandidate> townshipCandidates;

    public Township() {
        this.townshipCandidates = new HashSet<>();
    }

    public Township(Constituency constituency, String location) {
        this.constituency = constituency;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
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

    public String getLargestParty() {
        return townshipCandidates.stream()
                .collect(
                        Collectors.groupingBy(
                                tc -> tc.getCandidate().getAffiliation().getName(),
                                Collectors.summingInt(TownshipCandidate::getVotes)
                        )
                )
                .entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() - entry2.getValue())
                .map(entry -> entry.getKey() + " with " + entry.getValue() + " votes")
                .orElse("No votes recorded");
    }
}
