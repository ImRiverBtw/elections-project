/**
 * ForumPostRepository
 */

package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.ForumTag;
import com.election.electionbackend.entity.Post;
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

//    private ForumTagRepository forumTagRepository;



    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    public ForumPostRepository(ForumTagRepository forumTagRepository) {
//        this.forumTagRepository = forumTagRepository;
//    }

    /**
     * Method that retrieves a certain post by its id
     * @param id - the id of the post you want to retrieve
     * @return - the post with the matching id
     */
    public Post findById(Long id) {

        return em.find(Post.class, id);

    }

    /**
     * Method that retrieves a list of all posts
     * @return a list of all posts
     */
    public List<Post> findAll() {
        List<Post> forumPosts = em.createQuery("from Post").getResultList();
        return forumPosts;
    }

    /**
     * Method that saves/updates a post
     * @param forumPost the post you want to add/update
     * @return the post that is saved/updated
     */
    public Post save(Post forumPost) {

        // checks if the post already exists //
        if (forumPost.getId() == null) {

            // insert post //
            em.persist(forumPost);

        } else {

            // update post //
            em.merge(forumPost);
        }

        return forumPost;
    }

    /**
     * Dummydata
     */
    public void insertDummyPosts() {

        Post forumPost = new Post(12L,"titel", "testTag", "auteur (gebruiker)", "inhoud post", LocalDateTime.now());

        save(forumPost);
    }


}
