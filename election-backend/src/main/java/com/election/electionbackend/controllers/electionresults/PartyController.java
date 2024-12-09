package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.DTO.electionresult.CandidateDto;
import com.election.electionbackend.repositories.electionresults.VoteRepository;
import com.election.electionbackend.services.electionresult.CandidateService;
import com.election.electionbackend.services.electionresult.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/electionresult/party")
@RequiredArgsConstructor
public class PartyController {

    private final VoteService voteService;
    private final CandidateService candidateService;

    /**
     * Endpoint to get the total amount of votes for a specific party
     *
     * @param id The id of a party
     * @return An integer representing the amount of votes a party got
     */
    @GetMapping("/{id}/votes")
    public int getTotalVotesForParty(@PathVariable String id) {
        return voteService.getTotalVotesForParty(id);
    }

    @GetMapping("/{id}/candidates")
    public List<CandidateDto> getCandidatesForParty(@PathVariable String id) {
        return candidateService.getAllByParty(id);
    }
}
