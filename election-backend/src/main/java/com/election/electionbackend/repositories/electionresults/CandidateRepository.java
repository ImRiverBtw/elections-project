package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.Candidate;
import com.election.electionbackend.models.id.CandidateId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CandidateRepository {
    @PersistenceContext
    private EntityManager em;  // Injects the EntityManager to interact with the database.

    @Autowired
    private AffiliationRepository affiliationRepository;

    /**
     * Finds a Candidate by its composite ID.
     * @param id the composite ID of the Candidate (e.g., candidate number and affiliation ID).
     * @return the Candidate entity or null if not found.
     */
    public Candidate findById(CandidateId id) {
        return em.find(Candidate.class, id);
    }

    /**
     * Retrieves all Candidate records from the database.
     * @return a list of all Candidate entities.
     */
    public List<Candidate> findAll() {
        return em.createQuery("select c from Candidate c", Candidate.class).getResultList();
    }

    public void insertDummyData() {
        em.persist(new Candidate(1L,"Geert Wilders", affiliationRepository.findById(1L)));
        em.persist(new Candidate(1L,"Frans Timmermans", affiliationRepository.findById(2L)));
        em.persist(new Candidate(2L,"Esmah Lahlah", affiliationRepository.findById(2L)));
        em.persist(new Candidate(1L,"Dilan Yesilgoz", affiliationRepository.findById(3L)));
        em.persist(new Candidate(2L,"Sophie Hermans", affiliationRepository.findById(3L)));
        em.persist(new Candidate(1L,"Pieter Omtzigt", affiliationRepository.findById(4L)));
        em.persist(new Candidate(2L,"Nicolien van Vroonhoven-Kok", affiliationRepository.findById(4L)));
        em.persist(new Candidate(1L,"Rob Jetten", affiliationRepository.findById(5L)));
        em.persist(new Candidate(2L,"Jan Paternotte", affiliationRepository.findById(5L)));
        em.persist(new Candidate(1L,"Caroline van der Plas", affiliationRepository.findById(6L)));
        em.persist(new Candidate(2L,"Mona Keijzer", affiliationRepository.findById(6L)));
        em.persist(new Candidate(1L,"Henri Bontenbal", affiliationRepository.findById(7L)));
        em.persist(new Candidate(2L,"Eline Vedder", affiliationRepository.findById(7L)));
        em.persist(new Candidate(1L,"Lilian Marijnissen", affiliationRepository.findById(8L)));
        em.persist(new Candidate(2L,"Sandra Beckerman", affiliationRepository.findById(8L)));
        em.persist(new Candidate(1L,"Thierry Baudet", affiliationRepository.findById(9L)));
        em.persist(new Candidate(2L,"Freek Jansen", affiliationRepository.findById(9L)));
        em.persist(new Candidate(1L,"Anja Hazekamp", affiliationRepository.findById(10L)));
        em.persist(new Candidate(2L,"Frank Wassenberg", affiliationRepository.findById(10L)));

    }
}
