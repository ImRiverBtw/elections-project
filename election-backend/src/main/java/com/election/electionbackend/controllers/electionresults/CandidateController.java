package com.election.electionbackend.controllers.electionresults;

//import com.election.electionbackend.services.electionresult.CandidateService;
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
//    private final CandidateService candidateService;

    /**
     * Endpoint to get the total amount of votes for a specific candidate
     *
     * @param id The id of a candidate
     * @return An integer representing the amount of votes a candidate got
     */
    @GetMapping("/{id}/votes")
    public int getTotalVotesForCandidate(@PathVariable String id) {
        return voteService.getTotalVotesForCandidate(id);
    }
}
