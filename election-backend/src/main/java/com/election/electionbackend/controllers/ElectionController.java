package com.election.electionbackend.controllers;

import com.election.electionbackend.ElectionResultParser;
import com.election.electionbackend.entity.Affiliation;
import com.election.electionbackend.entity.Candidate;
import com.election.electionbackend.entity.Constituency;
import com.election.electionbackend.id.CandidateId;
import com.election.electionbackend.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
class ElectionController {

    @Autowired
    private AffiliationRepository affiliationRepo;

    @Autowired
    private PollingStationRepository pollingStationRepo;

    @Autowired
    private PollingStationCandidateRepository pollingStationCandidateRepo;
//
//    @Autowired
//    private ConstituencyRepository constituencyRepo;

    @Autowired
    private CandidateRepository candidateRepo;
    @Autowired
    private PollingStationRepository pollingStationRepository;
    @Autowired
    private ConstituencyRepository constituencyRepository;


    @GetMapping("/electionresult/constituency/{constituency_id}")
    public Map<String, Integer> getElectionResults(@PathVariable String constituency_id) {
        ElectionResultParser parser = new ElectionResultParser();
        String filePath = "election-backend/src/main/resources/election-results/constituency/Telling_TK2023_kieskring_" + constituency_id + ".eml.xml";
        return parser.parseElectionResults(filePath);
    }

    @GetMapping("/electionresult/constituencies")
    public List<Map<String, Object>> getConstituencies() {
        return constituencyRepository.findAll().stream().map(constituency -> {
            Map<String, Object> constituencyMap = new HashMap<>();
            constituencyMap.put("id", constituency.getId());
            constituencyMap.put("name", constituency.getName());
            return constituencyMap;
        }).toList();
    }

    @GetMapping("/electionresult/constituency/{constituencyId}/votes")
    public List<AffiliationVoteDTO> getVotesByConstituency(@PathVariable Long constituencyId) {
        return pollingStationCandidateRepo.getVotesByConstituency(constituencyId);
    }

    @GetMapping("/electionresult/affiliation")
    public List<Affiliation> getAffiliations() {
        return affiliationRepo.findAll();
    }

    @GetMapping("/electionresult/affiliation/{affiliation_id}")
    public Affiliation getAffiliation(@PathVariable Long affiliation_id) {
        return affiliationRepo.findById(affiliation_id);
    }

    @GetMapping("/electionresult/affiliation/{affiliationId}/constituency/{constituencyId}/votes")
    public int getVotesForAffiliationInConstituency(
            @PathVariable Long affiliationId,
            @PathVariable Long constituencyId) {
        if (affiliationId == null || constituencyId == null) {
            throw new IllegalArgumentException("Affiliation ID and Constituency ID must not be null.");
        }
        return pollingStationCandidateRepo.getVotesForAffiliation(affiliationId, constituencyId);
    }



    @GetMapping("/electionresults/affiliation/{affiliation_id}/seats")
    public int getTotalSeatsForAffiliation(@PathVariable Long affiliation_id) {
        return affiliationRepo.getSeatCount(affiliation_id);
    }



    @GetMapping("/electionresult/affiliation/{affiliation_id}/votes")
    public int getTotalVotesForAffiliation(@PathVariable Long affiliation_id) {
//        Affiliation affiliation = affiliationRepo.findById(affiliation_id);
//        List<Candidate> candidates = affiliation.getCandidates();
//        List<PollingStation> pollingStations = pollingStationRepo.findAll();
//
//        int votes = 0;
//        for (Candidate candidate : candidates) {
//            for (PollingStation pollingStation : pollingStations) {
//                PollingStationCandidateId id = new PollingStationCandidateId(pollingStation.getId(), candidate.getId());
//                PollingStationCandidate pollingStationCandidate = pollingStationCandidateRepo.findById(id);
//                if (pollingStationCandidate != null) {
//                    votes += pollingStationCandidate.getVotes();
//                }
//            }
//        }
//        return votes;
        return affiliationRepo.getVoteCount(affiliation_id);
    }

    @GetMapping("/electionresult/affiliation/{affiliation_id}/{candidate_id}")
    public Candidate getCandidate(@PathVariable Long affiliation_id, @PathVariable Long candidate_id) {
        return candidateRepo.findById(new CandidateId(affiliation_id, candidate_id));
    }

    @GetMapping("electionresult/pollingstation/{pollingstation_id}/largest_affiliation")
    public Object[] getLargestAffiliation(@PathVariable Long pollingstation_id) {
        return pollingStationRepo.getBiggestAffiliation(pollingstation_id);
    }
}