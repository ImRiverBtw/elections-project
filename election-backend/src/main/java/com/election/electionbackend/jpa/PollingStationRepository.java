package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Constituency;
import com.election.electionbackend.entity.PollingStation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class PollingStationRepository {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    ConstituencyRepository constituencyRepository;

    public PollingStation findById(Long id) {
        return em.find(PollingStation.class, id);
    }

    public List<PollingStation> findAll() {
        return em.createQuery("from PollingStation", PollingStation.class).getResultList();
    }

    public void insertDummyData() {
        String[] pollingStationNames = new String[]{
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
            PollingStation pollingStation1 = new PollingStation(constituency, pollingStationNames[index++]);
            PollingStation pollingStation2 = new PollingStation(constituency, pollingStationNames[index++]);
            constituency.addPollingStation(pollingStation1);
            constituency.addPollingStation(pollingStation2);
            em.persist(pollingStation1);
            em.persist(pollingStation2);
        }
    }
}
