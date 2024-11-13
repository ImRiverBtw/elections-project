package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Constituency;
import com.election.electionbackend.entity.Township;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class TownshipRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    ConstituencyRepository constituencyRepository;

    public Township findById(Long id) {
        return em.find(Township.class, id);
    }

    public List<Township> findAll() {
        return em.createQuery("from Township", Township.class).getResultList();
    }

    public void insertDummyData() {
        String[] townshipNames = new String[]{
                "Groningen1", "Groningen2",
                "Leeuwarden1", "Leeuwarden2",
                "Assen1", "Assen2",
                "Zwolle1", "Zwolle2",
                "Lelystad1", "Lelystad2",
                "Nijmegen1", "Nijmegen2",
                "Arnhem1", "Arnhem2",
                "Utrecht1", "Utrecht2",
                "Amsterdam1", "Amsterdam2"
        };
        List<Constituency> constituencies = constituencyRepository.findAll();
        int index = 0;
        for (Constituency constituency : constituencies) {
            Township township1 = new Township(constituency, townshipNames[index++]);
            Township township2 = new Township(constituency, townshipNames[index++]);
            constituency.addTownship(township1);
            constituency.addTownship(township2);
            em.persist(township1);
            em.persist(township2);
        }
    }
}
