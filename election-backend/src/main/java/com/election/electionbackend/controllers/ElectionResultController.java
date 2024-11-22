package com.election.electionbackend.controllers;

import com.election.electionbackend.ElectionResultParser;
import com.election.electionbackend.entity.Affiliation;
import com.election.electionbackend.entity.Candidate;
import com.election.electionbackend.entity.PollingStation;
import com.election.electionbackend.id.CandidateId;
import com.election.electionbackend.jpa.AffiliationRepository;
import com.election.electionbackend.jpa.CandidateRepository;
import com.election.electionbackend.jpa.PollingStationCandidateRepository;
import com.election.electionbackend.jpa.PollingStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/electionresult")
class ElectionResultController {

    @Autowired
    private AffiliationRepository affiliationRepo;

    @Autowired
    private PollingStationRepository pollingStationRepo;

    @Autowired
    private PollingStationCandidateRepository pollingStationCandidateRepo;

    @Autowired
    private CandidateRepository candidateRepo;

    @Autowired
    private PollingStationRepository pollingStationRepository;

//    @Autowired
//    private ConstituencyRepository constituencyRepo;



    @GetMapping("/constituency/{constituency_id}")
    public Map<String, Integer> getElectionResults(@PathVariable String constituency_id) {
        ElectionResultParser parser = new ElectionResultParser();
        String filePath = "election-backend/src/main/resources/election-results/constituency/Telling_TK2023_kieskring_" + constituency_id + ".eml.xml";
        return parser.parseElectionResults(filePath);
    }

    @GetMapping("/affiliation")
    //return all affiliations
    public List<Affiliation> getAffiliations() {
        return affiliationRepo.findAll();
    }

    @GetMapping("/affiliation/{affiliation_id}")
    //return a specific affiliation
    public Affiliation getAffiliation(@PathVariable Long affiliation_id) {
        return affiliationRepo.findById(affiliation_id);
    }

    @GetMapping("/affiliation/{affiliation_id}/seats")
    public int getTotalSeatsForAffiliation(@PathVariable Long affiliation_id) {
        if (affiliationRepo == null) {
            throw new IllegalStateException("AffiliationRepository is not initialized!");
        }
        return affiliationRepo.getSeatCount(affiliation_id);
    }


    @GetMapping("/affiliation/{affiliation_id}/votes")
    //return the amount of votes for a specific affiliation
    public int getTotalVotesForAffiliation(@PathVariable Long affiliation_id) {
        return affiliationRepo.getVoteCount(affiliation_id);
    }

    @GetMapping("/affiliation/{affiliation_id}/{candidate_id}")
    //returns info for a specific candidate within a party
    public Candidate getCandidate(@PathVariable Long affiliation_id, @PathVariable Long candidate_id) {
        return candidateRepo.findById(new CandidateId(affiliation_id, candidate_id));
    }

    @GetMapping("/pollingstation/{pollingstation_id}/largest_affiliation")
    //return the largest affiliation in the given municipality
    public Object[] getLargestAffiliation(@PathVariable Long pollingstation_id) {
        return pollingStationRepo.getBiggestAffiliation(pollingstation_id);
    }

    @GetMapping("/pollingStation")
    public List<PollingStation> getAllTownships() {
        List<PollingStation> pollingStations = pollingStationRepo.findAll();
        System.out.println("Fetched polling stations: " + pollingStations); // Debugging log
        return pollingStations;
    }


    @GetMapping("/pollingStation/{pollingstation_id}")
    public Optional<PollingStation> getPollingStationById(@PathVariable Long pollingstation_id) {
        return pollingStationRepo.findById(pollingstation_id);
    }

}