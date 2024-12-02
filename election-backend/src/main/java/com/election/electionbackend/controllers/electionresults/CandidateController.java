package com.election.electionbackend.controllers.electionresults;


import com.election.electionbackend.services.electionresult.CandidateService;
import com.election.electionbackend.services.electionresult.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CandidateController {

    private final VoteService voteService;
    private final CandidateService candidateService;

    @GetMapping("/{candidate_id}/votes")
    public int getTotalVotesForCandidate(@PathVariable String candidate_id) {
        return voteService.getTotalVotesForCandidate(candidate_id);
    }
}
