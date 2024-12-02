package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.repositories.electionresults.VoteRepository;
import com.election.electionbackend.services.electionresult.VoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/electionresult/party")
public class PartyController {

    private final VoteService voteService;

    public PartyController(VoteRepository voteRepository, VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping("/{party_id}/votes")
    public int getTotalVotesForParty(@PathVariable String party_id) {
        return voteService.getTotalVotesForParty(party_id);
    }
}
