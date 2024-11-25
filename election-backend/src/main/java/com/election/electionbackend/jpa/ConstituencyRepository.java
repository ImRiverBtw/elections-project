package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Constituency;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ConstituencyRepository {
    @PersistenceContext
    private EntityManager em;

    public Constituency findById(Long id) {
        return em.find(Constituency.class, id);
    }

    public List<Constituency> findAll() {
        return em.createQuery("FROM Constituency", Constituency.class).getResultList();
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
