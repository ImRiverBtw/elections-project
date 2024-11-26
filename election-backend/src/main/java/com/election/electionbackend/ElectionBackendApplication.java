package com.election.electionbackend;

import com.election.electionbackend.repositories.electionresults.*;
import com.election.electionbackend.repositories.forum.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElectionBackendApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(ElectionBackendApplication.class);

    @Autowired
    private AffiliationRepository affiliationRepo;

    @Autowired
    private PollingStationRepository pollingStationRepo;

    @Autowired
    private PollingStationCandidateRepository pollingStationCandidateRepo;

    @Autowired
    private ConstituencyRepository constituencyRepo;

    @Autowired
    private CandidateRepository candidateRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting Election Backend");
        insertDummyData();
    }



    public void insertDummyData() {
        affiliationRepo.insertDummyData();
        constituencyRepo.insertDummyData();
        candidateRepo.insertDummyData();
        pollingStationRepo.insertDummyData();
        pollingStationCandidateRepo.insertDummyData();

        userRepo.insertDummyData();
    }

    public static void main(String[] args) {
        SpringApplication.run(ElectionBackendApplication.class, args);
    }
}