package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.Constituency;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ConstituencyRepository {
    @PersistenceContext
    private EntityManager em;  // Injects the EntityManager to interact with the database.

    /**
     * Finds a Constituency by its ID.
     * @param id the ID of the Constituency.
     * @return the Constituency entity or null if not found.
     */
    public Constituency findById(Long id) {
        return em.find(Constituency.class, id);
    }

    /**
     * Retrieves all Constituency records from the database.
     * @return a list of all Constituency entities.
     */
    public List<Constituency> findAll() {
        return em.createQuery("from Constituency").getResultList();
    }

    public void insertDummyData() {
        String[] constituencyNames = new String[]{
                "Groningen", "Leeuwarden", "Assen", "Zwolle", "Lelystad", "Nijmegen", "Arnhem", "Utrecht", "Amsterdam"
        };
        for (String name : constituencyNames) {
            Constituency constituency = new Constituency(name);
            em.persist(constituency);
        }
    }
}
