package com.election.electionbackend.controllers;


import com.election.electionbackend.entity.ForumPost;
import com.election.electionbackend.jpa.ForumPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumPostController {

    @Autowired
    private ForumPostRepository forumPostRepository;

    @GetMapping("/forumpost")
    public List<ForumPost> getAllForumPosts() {

        List <ForumPost> forumPosts = forumPostRepository.findAll();

        return forumPosts;

    }


}
