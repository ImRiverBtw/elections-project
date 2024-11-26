package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.Affiliation;
import com.election.electionbackend.entity.Users;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class UserRepository {

    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    /**
     * Voegt dummy data toe aan de database
     */
    public void insertDummyData() {
        String[] usernames = {"john_doe", "jane_doe", "alice_smith", "bob_jones"};
        String[] emails = {"john@example.com", "jane@example.com", "alice@example.com", "bob@example.com"};
        String[] passwords = {"password123", "password456", "password789", "password101"};

        for (int i = 0; i < usernames.length; i++) {
            Users user = new Users();
            user.setUsername(usernames[i]);
            user.setEmail(emails[i]);
            user.setPassword(passwords[i]);
            em.persist(user);
        }
    }

    /**
     * Zoekt naar een gebruiker op basis van de gebruikersnaam
     * @param username Voor het vinden van de usernaam om te checken of de usernaam al in de database staat
     * @return
     */
    public Users findByUsername(String username) {
        return em.createQuery("SELECT u from Users u where u.username = :username", Users.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Controleert of een gebruiker met de opgegeven gebruikersnaam of e-mailadres al bestaat.
     *
     * @param username de gebruikersnaam om te controleren.
     * @return true als een gebruiker met de gebruikersnaam of het e-mailadres al bestaat, anders false.
     */
    public boolean existsByUsername(String username) {
        return em.createQuery("SELECT COUNT(u) FROM Users u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult() > 0;
    }

    public boolean existsByEmail(String email) {
        return em.createQuery("SELECT COUNT(u) FROM Users u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0;
    }

    /**
     * Zoekt een gebruiker op basis van het e-mailadres.
     *
     * @param email het e-mailadres van de te zoeken gebruiker.
     * @return de gevonden gebruikersentiteit, of null als deze niet gevonden wordt.
     */
    public Users findByEmail(String email) {
        return em.createQuery("SELECT u from Users u WHERE u.email = :email", Users.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Slaat een gebruiker op in de database.
     *
     * @param users de gebruiker die opgeslagen moet worden.
     */
    public void save(Users users) {
        em.persist(users);
    }

    /**
     * Haalt alle gebruikers op uit de database.
     *
     * @return een lijst met alle gebruikers.
     */
    public List<Users> findAll() {
        return em.createQuery("from Users", Users.class).getResultList();
    }

    /**
     * Zoekt een gebruiker op basis van het ID.
     *
     * @param id het ID van de te zoeken gebruiker.
     * @return de gevonden gebruikersentiteit, of null als deze niet gevonden wordt.
     */
    public Users findById(Long id) {
        return em.find(Users.class, id);
    }
}
