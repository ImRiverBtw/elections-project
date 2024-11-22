package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Affiliation;
import com.election.electionbackend.entity.Candidate;
import com.election.electionbackend.entity.PollingStation;
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
    private EntityManager em; // Injects the EntityManager to interact with the database.

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Finds an Affiliation by its ID.
     * @param id the ID of the Affiliation.
     * @return the Affiliation entity or null if not found.
     */
    public Affiliation findById(Long id) {
        return em.find(Affiliation.class, id);
    }

    /**
     * Retrieves all Affiliation records from the database.
     * @return a list of all Affiliation entities.
     */
    public List<Affiliation> findAll() {
        return em.createQuery("from Affiliation").getResultList();
    }

    /**
     * Retrieves all Candidates associated with a specific Affiliation.
     * @param affiliationId the ID of the Affiliation.
     * @return a list of Candidate entities linked to the given Affiliation.
     */
    public List<Candidate> getCandidates(Long affiliationId) {
        Affiliation affiliation = findById(affiliationId);
        return affiliation.getCandidates();
    }

    /**
     * Saves an Affiliation entity to the database. Performs insert or update depending on whether the ID is null.
     * @param affiliation the Affiliation entity to save.
     * @return the persisted or merged Affiliation entity.
     */
    public Affiliation save(Affiliation affiliation) {
        if (affiliation.getId() == null) {
            //insert a new affiliation
            em.persist(affiliation);
        } else {
            //update an existing affiliation
            em.merge(affiliation);
        }
        return affiliation;
    }

    /**
     * Calculates the total vote count for a given Affiliation.
     * @param affiliationId the ID of the Affiliation.
     * @return the total number of votes for the Affiliation.
     */
    public int getVoteCount(Long affiliationId){
        Affiliation affiliation = findById(affiliationId);
        TypedQuery<Long> query = em.createQuery( "Select sum(psc.votes) From PollingStationCandidate psc JOIN psc.candidate c WHERE  c.affiliation = :affiliation", Long.class );
       query.setParameter("affiliation", affiliation);
        logger.info(String.valueOf(query.getSingleResult()));
        return query.getSingleResult().intValue();

    }
    public int getVoteCount(Long affiliationId, Long PollingStationId){
        Affiliation affiliation = findById(affiliationId);
        PollingStation pollingStation = em.find(PollingStation.class, PollingStationId);
        TypedQuery<Long> query = em.createQuery( "Select sum(psc.votes) From PollingStationCandidate psc JOIN psc.candidate c WHERE  c.affiliation = :affiliation AND  psc.pollingStation = :pollingStation ", Long.class );
        query.setParameter("affiliation", affiliation);
        query.setParameter("pollingStation", pollingStation);
        logger.info(String.valueOf(query.getSingleResult()));
        return query.getSingleResult().intValue();

    }


    /**
     * Calculates the number of seats allocated to a given Affiliation based on proportional representation.
     * @param affiliationId the ID of the Affiliation.
     * @return the number of seats allocated to the Affiliation.
     */
    public int getSeatCount(Long affiliationId){
        //get the total amount of votes across all affiliations
        TypedQuery<Long> query = em.createQuery( "Select sum(psc.votes) From PollingStationCandidate psc", Long.class );
        int totalVotes = query.getSingleResult().intValue();
        int votesPerSeat = (int) Math.ceil((double) totalVotes / 150);//calculate the amount of votes needed to get a seat
        System.out.println(getVoteCount(affiliationId) / votesPerSeat);
        return getVoteCount(affiliationId) / votesPerSeat; //return the number of seats
        //TODO: adjust calculations to account for residual seats.
    }

    /**
     * Creates a trimmed and sorted list off affiliations and assigns their seatcount
     * @return A list of Affiliation entities with updated seatCount values
     */
    public List<Affiliation> getSeatResults(){
        //fetch all affiliations
        List<Affiliation> affiliations = findAll();

        //add seatcount to affiliation if it doesnt exist
        for (Affiliation affiliation : affiliations) {
            if (affiliation.getSeatCount() == null) {
                int seatCount = getSeatCount(affiliation.getId());
                affiliation.setSeatCount(seatCount);
                save(affiliation);
            }
        }
        //trim and sort the list before returning
        affiliations.removeIf(affiliation -> affiliation.getSeatCount() == 0);
        affiliations.sort((a, b ) -> Integer.compare(b.getSeatCount(), a.getSeatCount()));
        return affiliations;
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
