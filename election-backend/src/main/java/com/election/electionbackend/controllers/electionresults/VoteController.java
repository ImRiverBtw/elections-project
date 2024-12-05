package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.services.electionresult.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electionresult/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    /**
     * Endpoint to get total votes or seats by party.
     *
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
                return ResponseEntity.badRequest().body("Invalid resultType");
        }
    }

    /**
     * Endpoint to get seat count for a specific party.
     *
     * @param id The ID of a party.
     * @return Number of seats allocated to the party.
     */
    @GetMapping("/affiliation/{id}/seats")
    public ResponseEntity<Integer> getSeatCountForParty(@PathVariable String id) {
        return ResponseEntity.ok(voteService.getTotalSeatsForParty(id));
    }
}
