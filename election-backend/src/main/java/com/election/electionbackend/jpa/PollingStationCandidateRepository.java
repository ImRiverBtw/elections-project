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
import org.springframework.beans.factory.annotation.Autowired;
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
    private EntityManager em;  // Injects the EntityManager to interact with the database.

    @Autowired
    public PollingStationCandidateRepository(PollingStationRepository pollingStationRepository, CandidateRepository candidateRepository) {
        this.pollingStationRepository = pollingStationRepository;
        this.candidateRepository = candidateRepository;
    }

    /**
     * Finds a PollingStationCandidate by its composite ID.
     * @param id the composite ID (polling station ID and candidate ID).
     * @return the PollingStationCandidate entity or null if not found.
     */
    public PollingStationCandidate findById(PollingStationCandidateId id) {
        return em.find(PollingStationCandidate.class, id);
    }

    /**
     * Finds a PollingStationCandidate based on the candidate and polling station.
     * @param candidate the Candidate entity.
     * @param pollingStation the PollingStation entity.
     * @return the corresponding PollingStationCandidate entity.
     */
    public PollingStationCandidate find(Candidate candidate, PollingStation pollingStation) {
        PollingStationCandidateId id = new PollingStationCandidateId(pollingStation.getId(), candidate.getId());
        return findById(id);
    }

    /**
     * Saves a PollingStationCandidate to the database.
     * Performs an insert if the ID is null, or an update otherwise.
     * @param pollingStationCandidate the entity to save.
     * @return the persisted or merged PollingStationCandidate entity.
     */
    public PollingStationCandidate save(PollingStationCandidate pollingStationCandidate) {
        if (pollingStationCandidate.getId() == null) {
            //generate a composite id for a new pollingstationcandidate
            Long pollingStationId = pollingStationCandidate.getPollingStation().getId();
            CandidateId candidateId = pollingStationCandidate.getCandidate().getId();
            //insert a new pollingstationcandidate
            pollingStationCandidate.setId(new PollingStationCandidateId(pollingStationId, candidateId));
            em.persist(pollingStationCandidate);
        } else {
            //update an existing pollingstationcandidate
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

    /**
     * Retrieves the number of votes for a given PollingStationCandidate.
     * @param pollingStationCandidate the PollingStationCandidate entity.
     * @return the number of votes recorded for the candidate in the polling station.
     */
    public int getVotes(PollingStationCandidate pollingStationCandidate) {
        return pollingStationCandidate.getVotes();
    }


    public void insertDummyData() {
        //retrieves a list of all pollingstations and a list of all candidates
        List<PollingStation> pollingStations = pollingStationRepository.findAll();
        List<Candidate> candidates = candidateRepository.findAll();
        //loops over all the combinations of a pollingstation and a candidate
        for (PollingStation pollingStation : pollingStations) {
            for (Candidate candidate : candidates) {
                //creates a new pollingstationcandidate entity with a random amount of votes
                PollingStationCandidate pollingStationCandidate = new PollingStationCandidate(pollingStation, candidate);
                Random random = new Random();
                int randomVoteCount = random.nextInt(100000);
                pollingStationCandidate.setVotes(randomVoteCount);
                //save the entity to the database
                save(pollingStationCandidate);
            }
        }
    }
}
