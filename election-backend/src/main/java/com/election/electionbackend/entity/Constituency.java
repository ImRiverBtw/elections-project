package com.election.electionbackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<Township> townships;

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

    public List<Township> getTownships() {
        return townships;
    }

    public void addPollingStation(Township pollingStation) {
        townships.add(pollingStation);
    }

    public void addTownship(Township township) {
        townships.add(township);
    }

    public void removeTownship(Township township) {
        townships.remove(township);
    }
}
