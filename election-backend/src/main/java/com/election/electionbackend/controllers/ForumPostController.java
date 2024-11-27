/**
 * ForumPostController
 * Controller that communicates between the repository and the front end by calling the api's listing below
 */

package com.election.electionbackend.controllers;


import com.election.electionbackend.entity.Post;
import com.election.electionbackend.jpa.ForumPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumPostController {

    @Autowired
    private ForumPostRepository forumPostRepository;


    /**
     * Method that gets all existing posts from the database
     * @return a list of all the forum posts
     */
    @GetMapping("/forumposts")
    public List<Post> getAllForumPosts() {

        List <Post> forumPosts = forumPostRepository.findAll();

        return forumPosts;

    }

    /**
     * Method that inserts a new post in the database using the repository
     * @param forumPost - the post that will be inserted to the db
     * @return a ResponseEntity telling if the post was created succefully
     */
    @PostMapping("/forumNewPost")
    public ResponseEntity<String> createNewPost(@RequestBody Post forumPost) {

        forumPostRepository.save(forumPost);

        return ResponseEntity.ok("Forum post created successfully");
    }


}
