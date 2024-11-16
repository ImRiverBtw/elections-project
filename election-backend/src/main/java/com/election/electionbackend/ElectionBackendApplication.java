package com.election.electionbackend;

import com.election.electionbackend.entity.*;
import com.election.electionbackend.id.CandidateId;
import com.election.electionbackend.jpa.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ElectionBackendApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(ElectionBackendApplication.class);

    @Autowired
    private AffiliationRepository affiliationRepo;

    @Autowired
    private TownshipRepository townshipRepo;

    @Autowired
    private TownshipCandidateRepository townshipCandidateRepo;

    @Autowired
    private ConstituencyRepository constituencyRepo;

    @Autowired
    private CandidateRepository candidateRepo;

    public static void main(String[] args) {
        SpringApplication.run(ElectionBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Election Backend");
        insertDummyData();
    }

    public void insertDummyData() {
        affiliationRepo.insertDummyData();
        constituencyRepo.insertDummyData();
        candidateRepo.insertDummyData();
        townshipRepo.insertDummyData();
        townshipCandidateRepo.insertDummyData();
    }
}

@RestController
class ElectionResultController {

    @Autowired
    private AffiliationRepository affiliationRepo;

//    @Autowired
//    private TownshipRepository townshipRepo;

//    @Autowired
//    private TownshipCandidateRepository townshipCandidateRepo;

//    @Autowired
//    private ConstituencyRepository constituencyRepo;

    @Autowired
    private ElectionService electionService;

    @Autowired
    private CandidateRepository candidateRepo;


    @GetMapping("/electionresult/constituency/{constituency_id}")
    public ResponseEntity<Map<String, Integer>> getElectionResults(@PathVariable String constituency_id) {
        try {
            Map<String, Integer> results = electionService.getElectionResults(constituency_id);
            if (results.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Internal Server Error
        }
    }

    @GetMapping("/electionresult/affiliation")
    public List<Affiliation> getAffiliations() {
        return affiliationRepo.findAll();
    }

    @GetMapping("/electionresult/affiliation/{affiliation_id}")
    public ResponseEntity<Affiliation> getAffiliation(@PathVariable Long affiliation_id) {
        return affiliationRepo.findById(affiliation_id)
                .map(affiliation -> ResponseEntity.ok(affiliation)) // Explicitly pass the body
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/electionresult/affiliation/{affiliation_id}/seats")
    public int getTotalSeatsForAffiliation(@PathVariable Long affiliation_id) {
        return affiliationRepo.getSeatCount(affiliation_id);
    }

    @GetMapping("/electionresult/affiliation/{affiliation_id}/{candidate_id}")
    public Candidate getCandidate(@PathVariable Long affiliation_id, @PathVariable Long candidate_id) {
        return candidateRepo.findById(new CandidateId(affiliation_id, candidate_id)).orElse(null);
    }

    @GetMapping("/electionresult/affiliation/{affiliation_id}/votes")
    public int getTotalVotesForAffiliation(@PathVariable Long affiliation_id) {
//        Affiliation affiliation = affiliationRepo.findById(affiliation_id);
//        List<Candidate> candidates = affiliation.getCandidates();
//        List<Township> townships = townshipRepo.findAll();
//
//        int votes = 0;
//        for (Candidate candidate : candidates) {
//            for (Township township : townships) {
//                TownshipCandidateId id = new TownshipCandidateId(township.getId(), candidate.getId());
//                TownshipCandidate townshipCandidate = townshipCandidateRepo.findById(id);
//                if (townshipCandidate != null) {
//                    votes += townshipCandidate.getVotes();
//                }
//            }
//        }
//        return votes;
        return affiliationRepo.getVoteCount(affiliation_id);
    }
}