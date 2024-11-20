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

import java.util.List;

@Repository
@Transactional
public class AffiliationRepository{
    @PersistenceContext
    private EntityManager em;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Affiliation findById(Long id) {
        return em.find(Affiliation.class, id);
    }
    public List<Affiliation> findAll() {
        return em.createQuery("from Affiliation").getResultList();
    }
    public List<Candidate> getCandidates(Long affiliationId) {
        Affiliation affiliation = findById(affiliationId);
        return affiliation.getCandidates();
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

    public int getVoteCount(Long affiliationId){
        Affiliation affiliation = findById(affiliationId);
        TypedQuery<Long> query = em.createQuery( "Select sum(psc.votes) From PollingStationCandidate psc JOIN psc.candidate c WHERE  c.affiliation = :affiliation", Long.class );
       query.setParameter("affiliation", affiliation);
        logger.info(String.valueOf(query.getSingleResult()));
        return query.getSingleResult().intValue();

    }

    public int getSeatCount(Long affiliationId){
        TypedQuery<Long> query = em.createQuery( "Select sum(psc.votes) From PollingStationCandidate psc", Long.class );
        int totalVotes = query.getSingleResult().intValue();
        int votesPerSeat = (int) Math.ceil((double) totalVotes / 150);
        return getVoteCount(affiliationId) / votesPerSeat;
        //TODO: rekening houden met restzetels
    }

    public void insertDummyData(){
        String[] affiliationNames = new String[] {
                "PVV", "GLPVDA", "VVD", "NSC", "D66", "BBB", "CDA", "SP", "FVD", "PVDD"
        };
        for (String name : affiliationNames) {
            em.persist(new Affiliation(name));
        }
    }
}
