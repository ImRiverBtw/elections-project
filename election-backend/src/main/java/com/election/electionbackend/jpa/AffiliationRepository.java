package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Affiliation;
import com.election.electionbackend.entity.Candidate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class AffiliationRepository{
    @PersistenceContext
    private EntityManager em;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Optional<Affiliation> findById(Long id) {
        return Optional.ofNullable(em.find(Affiliation.class, id));
    }

    public List<Affiliation> findAll() {
        return em.createQuery("from Affiliation", Affiliation.class).getResultList();
    }

    public List<Candidate> getCandidates(Long affiliationId) {
        Optional<Affiliation> affiliationOpt = findById(affiliationId);
        return affiliationOpt.map(Affiliation::getCandidates).orElse(Collections.emptyList());
    }

    public Affiliation save(Affiliation affiliation) {
        if (affiliation.getId() == null) {
            //insert
            em.persist(affiliation);
        } else {
            //update
            em.merge(affiliation);
        }
        return affiliation;
    }

    public int getVoteCount(Long affiliationId) {
        Optional<Affiliation> affiliation = findById(affiliationId);
        TypedQuery<Long> query = em.createQuery(
                "SELECT SUM(psc.votes) FROM TownshipCandidate psc JOIN psc.candidate c WHERE c.affiliation = :affiliation",
                Long.class
        );
        query.setParameter("affiliation", affiliation);
        return query.getSingleResult().intValue();
    }

    public int getSeatCount(Long affiliationId){
        TypedQuery<Long> query = em.createQuery( "Select sum(psc.votes) From TownshipCandidate psc", Long.class );
        int totalVotes = query.getSingleResult().intValue();
        int votesPerSeat = (int) Math.ceil((double) totalVotes / 150);
        return getVoteCount(affiliationId) / votesPerSeat;
        //TODO: rekening houden met restzetels
    }

    public void insertDummyData() {
        String[] affiliationNames = {"PVV", "GLPVDA", "VVD", "NSC", "D66", "BBB", "CDA", "SP", "FVD", "PVDD"};
        for (String name : affiliationNames) {
            Affiliation affiliation = new Affiliation();
            affiliation.setName(name);
            em.persist(affiliation);
        }
    }
}
