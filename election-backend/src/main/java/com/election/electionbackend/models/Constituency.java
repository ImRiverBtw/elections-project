package com.election.electionbackend.models;

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

    public Constituency() {}

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
}
