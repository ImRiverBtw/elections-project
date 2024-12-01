package com.election.electionbackend.controllers.electionresults;


import com.election.electionbackend.services.electionresult.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electionresult/votes")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    /**
     * Endpoint to get total votes or seats by party.
     * @return List of NewAggregatedVoteDto or NewAggregatedSeatDto containing details for each party.
     */
    @GetMapping("/affiliation")
    public ResponseEntity<?> getResultsByParty(@RequestHeader String resultType) {
        switch (resultType) {
            case "vote":
                return ResponseEntity.ok(voteService.getTotalVotesByParty());
            case "seat":
                return ResponseEntity.ok(voteService.getTotalSeatsByParty());
            default:
                return ResponseEntity.badRequest().body("Invalid responseType");
        }
    }
}
