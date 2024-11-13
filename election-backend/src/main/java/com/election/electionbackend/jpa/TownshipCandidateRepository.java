package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Candidate;
import com.election.electionbackend.entity.Township;
import com.election.electionbackend.entity.TownshipCandidate;
import com.election.electionbackend.id.CandidateId;
import com.election.electionbackend.id.TownshipCandidateId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class TownshipCandidateRepository {
    private final TownshipRepository townshipRepository;
    private final CandidateRepository candidateRepository;
    @PersistenceContext
    private EntityManager em;

    public TownshipCandidateRepository(TownshipRepository townshipRepository, CandidateRepository candidateRepository) {
        this.townshipRepository = townshipRepository;
        this.candidateRepository = candidateRepository;
    }

    public TownshipCandidate findById(TownshipCandidateId id) {
        return em.find(TownshipCandidate.class, id);
    }

    public TownshipCandidate find(Candidate candidate, Township township) {
        TownshipCandidateId id = new TownshipCandidateId(township.getId(), candidate.getId());
        return findById(id);
    }

    public TownshipCandidate save(TownshipCandidate townshipCandidate) {
        if (townshipCandidate.getId() == null) {
            //insert
            Long townshipId = townshipCandidate.getTownship().getId();
            CandidateId candidateId = townshipCandidate.getCandidate().getId();
            townshipCandidate.setId(new TownshipCandidateId(townshipId, candidateId));
            em.persist(townshipCandidate);
        } else {
            //update
            em.merge(townshipCandidate);
        }
        return townshipCandidate;
    }

    public int getVotes(TownshipCandidate townshipCandidate) {
        return townshipCandidate.getVotes();
    }

    public void insertDummyData() {
        List<Township> townships = townshipRepository.findAll();
        List<Candidate> candidates = candidateRepository.findAll();
        for (Township township : townships) {
            for (Candidate candidate : candidates) {
                TownshipCandidate townshipCandidate = new TownshipCandidate(township, candidate);
                Random random = new Random();
                int randomVoteCount = random.nextInt(100000);
                townshipCandidate.setVotes(randomVoteCount);
                save(townshipCandidate);
            }
        }
    }
}
