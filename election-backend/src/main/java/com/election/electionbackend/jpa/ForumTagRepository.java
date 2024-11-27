package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.ForumTag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class ForumTagRepository {

    @PersistenceContext
    private EntityManager em;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ForumTag findById(Long id) {

        return em.find(ForumTag.class, id);

    }

    public List<ForumTag> findAll() {
        List<ForumTag> forumTags = em.createQuery("from ForumTag ").getResultList();
        return forumTags;
    }


    public ForumTag save(ForumTag forumTag) {

        // checks if the tag already exists //
        if (forumTag.getId() == null) {

            // insert new tag //
            em.persist(forumTag);

        }

        return forumTag;
    }

    public void insertDummyTags() {

        ForumTag forumTag = new ForumTag("PVV");
        ForumTag forumTag1 = new ForumTag("VVD");
        ForumTag forumTag2 = new ForumTag("GLPVDA");
        ForumTag forumTag3 = new ForumTag("CDA");
        ForumTag forumTag4 = new ForumTag("NSC");

        save(forumTag);
        save(forumTag1);
        save(forumTag2);
        save(forumTag3);
        save(forumTag4);
    }


}
