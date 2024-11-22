package com.election.electionbackend.models.electionresults;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Constituency {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy = "constituency")
    private List<PollingStation> pollingStations = new ArrayList<>();

    public Constituency() {
    }

    public Constituency(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PollingStation> getPollingStations() {
        return pollingStations;
    }

    public void addPollingStation(PollingStation pollingStation) {
        pollingStations.add(pollingStation);
    }

    public void removePollingStation(PollingStation pollingStation) {
        pollingStations.remove(pollingStation);
    }
}
