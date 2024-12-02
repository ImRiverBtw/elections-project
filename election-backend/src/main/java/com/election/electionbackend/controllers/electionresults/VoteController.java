package com.election.electionbackend.controllers.electionresults;


import com.election.electionbackend.services.electionresult.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/electionresult/votes")
public class VoteController {

    private final VoteService voteService;

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

    /**
     * Endpoint to get seat count for a specific party.
     * @param partyId ID of the party.
     * @return Number of seats allocated to the party.
     */
    @GetMapping("/affiliation/{partyId}/seats")
    public int getSeatCountForParty(@PathVariable String partyId) {
        return voteService.getTotalSeatsForParty(partyId);
    }
}
