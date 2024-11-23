package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.ForumPost;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class ForumPostRepository {

    @PersistenceContext
    private EntityManager em;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public ForumPost findById(Long id) {

        return em.find(ForumPost.class, id);

    }

    public List<ForumPost> findAll() {
        List<ForumPost> forumPosts = em.createQuery("from ForumPost").getResultList();
        return forumPosts;
    }

    public ForumPost save(ForumPost forumPost) {

        if (forumPost.getId() == null) {

            em.persist(forumPost);

        } else {

            em.merge(forumPost);
        }
        return forumPost;
    }

    public void insertDummyPosts() {

        ForumPost forumPost = new ForumPost(1224324234L,"titel", "tag", "auteur (gebruiker)", "inhoud post", LocalDateTime.now());

        save(forumPost);
    }


}
