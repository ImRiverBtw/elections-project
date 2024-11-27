package com.election.electionbackend.controllers;


import com.election.electionbackend.entity.ForumTag;
import com.election.electionbackend.jpa.ForumTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ForumTagController {

    @Autowired
    private ForumTagRepository forumTagRepository;

    @GetMapping("/forum/tags")
    public List<ForumTag> getAllForumTags() {

        List <ForumTag> forumTags = forumTagRepository.findAll();

        return forumTags;

    }


}
