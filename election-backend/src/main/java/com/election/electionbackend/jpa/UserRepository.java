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

    public void insertDummyData() {
        String[] usernames = {"john_doe", "jane_doe", "alice_smith", "bob_jones"};
        String[] emails = {"john@example.com", "jane@example.com", "alice@example.com", "bob@example.com"};
        String[] passwords = {"password123", "password456", "password789", "password101"};

        for (int i = 0; i < usernames.length; i++) {
            Users user = new Users();
            user.setUsername(usernames[i]);
            user.setEmail(emails[i]);
            user.setPassword(passwords[i]);
            em.persist(user); // Persist each user to the database
        }
    }

    public List<Users> findAll() {
        return em.createQuery("from Users", Users.class).getResultList();
    }

    public Users findById(Long id) {
        return em.find(Users.class, id);
    }
}
