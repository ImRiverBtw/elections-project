package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Candidate;
import com.election.electionbackend.entity.PollingStation;
import com.election.electionbackend.entity.PollingStationCandidate;
import com.election.electionbackend.id.CandidateId;
import com.election.electionbackend.id.PollingStationCandidateId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
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

    public int getVotesForAffiliation(Long affiliationId, Long constituencyId) {
        String queryStr = """
        SELECT SUM(psc.votes) 
        FROM PollingStationCandidate psc 
        JOIN psc.pollingStation ps 
        WHERE ps.constituency.id = :constituencyId AND psc.candidate.affiliation.id = :affiliationId
    """;
        TypedQuery<Long> query = em.createQuery(queryStr, Long.class);
        query.setParameter("constituencyId", constituencyId);
        query.setParameter("affiliationId", affiliationId);

        Long result = query.getSingleResult();
        return result != null ? result.intValue() : 0;
    }

    public List<AffiliationVoteDTO> getVotesByConstituency(Long constituencyId) {
        String queryStr = """
        SELECT new com.election.electionbackend.jpa.AffiliationVoteDTO(
            psc.candidate.affiliation.name,
            SUM(psc.votes)
        )
        FROM PollingStationCandidate psc
        JOIN psc.pollingStation ps
        WHERE ps.constituency.id = :constituencyId
        GROUP BY psc.candidate.affiliation.name
        ORDER BY SUM(psc.votes) DESC
    """;

        TypedQuery<AffiliationVoteDTO> query = em.createQuery(queryStr, AffiliationVoteDTO.class);
        query.setParameter("constituencyId", constituencyId);

        return query.getResultList();
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
