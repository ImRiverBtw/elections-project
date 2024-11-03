package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Affiliation;
import com.election.electionbackend.entity.Candidate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AffiliationRepository{
    @PersistenceContext
    private EntityManager em;

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

    public void insertDummyData(){
        String[] affiliationNames = new String[] {
                "PVV", "GLPVDA", "VVD", "NSC", "D66", "BBB", "CDA", "SP", "FVD", "PVDD"
        };
        for (String name : affiliationNames) {
            em.persist(new Affiliation(name));
        }
    }
}
