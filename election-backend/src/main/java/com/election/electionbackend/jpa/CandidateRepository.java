package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Candidate;
import com.election.electionbackend.id.CandidateId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CandidateRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private AffiliationRepository affiliationRepository;

    // Find candidate by composite ID (Affiliation ID and Candidate ID)
    public Optional<Candidate> findById(CandidateId id) {
        return Optional.ofNullable(em.find(Candidate.class, id));  // Wrap the result in Optional
    }


    // Find all candidates
    public List<Candidate> findAll() {
        return em.createQuery("select c from Candidate c", Candidate.class).getResultList();
    }

    // Insert dummy data for testing
    public void insertDummyData() {
        // Define the candidate data
        Object[][] candidateData = {
                {1L, "Geert Wilders", 1L},
                {1L, "Frans Timmermans", 2L},
                {2L, "Esmah Lahlah", 2L},
                {1L, "Dilan Yesilgoz", 3L},
                {2L, "Sophie Hermans", 3L},
                {1L, "Pieter Omtzigt", 4L},
                {2L, "Nicolien van Vroonhoven-Kok", 4L},
                {1L, "Rob Jetten", 5L},
                {2L, "Jan Paternotte", 5L},
                {1L, "Caroline van der Plas", 6L},
                {2L, "Mona Keijzer", 6L},
                {1L, "Henri Bontenbal", 7L},
                {2L, "Eline Vedder", 7L},
                {1L, "Lilian Marijnissen", 8L},
                {2L, "Sandra Beckerman", 8L},
                {1L, "Thierry Baudet", 9L},
                {2L, "Freek Jansen", 9L},
                {1L, "Anja Hazekamp", 10L},
                {2L, "Frank Wassenberg", 10L}
        };

        // Insert candidates into the database
        for (Object[] data : candidateData) {
            Long candidateId = (Long) data[0];
            String name = (String) data[1];
            Long affiliationId = (Long) data[2];

            // Fetch the affiliation, and ensure it's not null
            var affiliation = affiliationRepository.findById(affiliationId)
                    .orElseThrow(() -> new IllegalArgumentException("Affiliation not found with ID " + affiliationId));
                Candidate candidate = new Candidate(candidateId, name, affiliation);
                em.persist(candidate);

        }
    }
}
