package com.election.electionbackend.controllers;

import com.election.electionbackend.models.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class ElectionController {

//    private final PartyDAOService partyDAOService;
//
//    @Autowired
//    public ElectionController(PartyDAOService partyDAOService) {
//        this.partyDAOService = partyDAOService;
//    }
//
//    @GetMapping("/electionresult/party")
//    public List<Party> getParties() {
//        return partyDAOService.getAll();
//    }
//
//
//    @GetMapping("/electionresult/party/{party-id}")
//    public Party getParty(@PathVariable("party-id") int partyId) {
//        return partyDAOService.getPartyById(partyId);
//    }
//
//
//    @GetMapping("/electionresult/party/{party-id}/{candidate-id}")
//    public Candidate getCandidate(@PathVariable("party-id") int partyId, @PathVariable("candidate-id") int candidateId) {
//        return partyDAOService.getPartyById(partyId).getCandidate(candidateId);
//    }
}
