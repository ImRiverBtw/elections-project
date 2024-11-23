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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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
    private ForumPostRepository forumPostRepository;


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
        forumPostRepository.insertDummyPosts();
    }

    public static void main(String[] args) {
        SpringApplication.run(ElectionBackendApplication.class, args);
    }
}

