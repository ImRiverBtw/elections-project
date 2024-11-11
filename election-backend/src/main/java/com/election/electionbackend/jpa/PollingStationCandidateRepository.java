package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Candidate;
import com.election.electionbackend.entity.PollingStation;
import com.election.electionbackend.entity.PollingStationCandidate;
import com.election.electionbackend.id.CandidateId;
import com.election.electionbackend.id.PollingStationCandidateId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class PollingStationCandidateRepository {
    private final PollingStationRepository pollingStationRepository;
    private final CandidateRepository candidateRepository;
    @PersistenceContext
    private EntityManager em;

    public PollingStationCandidateRepository(PollingStationRepository pollingStationRepository, CandidateRepository candidateRepository) {
        this.pollingStationRepository = pollingStationRepository;
        this.candidateRepository = candidateRepository;
    }

    public PollingStationCandidate findById(PollingStationCandidateId id) {
        return em.find(PollingStationCandidate.class, id);
    }

    public PollingStationCandidate find(Candidate candidate, PollingStation pollingStation) {
        PollingStationCandidateId id = new PollingStationCandidateId(pollingStation.getId(), candidate.getId());
        return findById(id);
    }

    public PollingStationCandidate save(PollingStationCandidate pollingStationCandidate) {
        if (pollingStationCandidate.getId() == null) {
            //insert
            Long pollingStationId = pollingStationCandidate.getPollingStation().getId();
            CandidateId candidateId = pollingStationCandidate.getCandidate().getId();
            pollingStationCandidate.setId(new PollingStationCandidateId(pollingStationId, candidateId));
            em.persist(pollingStationCandidate);
        } else {
            //update
            em.merge(pollingStationCandidate);
        }
        return pollingStationCandidate;
    }

    public int getVotes(PollingStationCandidate pollingStationCandidate) {
        return pollingStationCandidate.getVotes();
    }

    public void insertDummyData() {
        List<PollingStation> pollingStations = pollingStationRepository.findAll();
        List<Candidate> candidates = candidateRepository.findAll();
        for (PollingStation pollingStation : pollingStations) {
            for (Candidate candidate : candidates) {
                PollingStationCandidate pollingStationCandidate = new PollingStationCandidate(pollingStation, candidate);
                Random random = new Random();
                int randomVoteCount = random.nextInt(100000);
                pollingStationCandidate.setVotes(randomVoteCount);
                save(pollingStationCandidate);
            }
        }
    }
}
