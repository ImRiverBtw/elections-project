package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto;
import com.election.electionbackend.services.electionresult.PollingStationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/electionresult/pollingstation")
@RequiredArgsConstructor
public class PollingStationController {


    private final PollingStationService pollingStationService;

    /**
     * Endpoint to get the the amount of votes for each party in a specific polling station
     *
     * @param id The id of a polling station
     * @return An integer representing the amount of votes a party got in this pollingstation
     */
    @GetMapping("/{id}/votes")
    public List<NewAggregatedVoteDto> getVotesByParty(@PathVariable String id) {
        return pollingStationService.getTotalVotesByPartyInPollingStation(id);
    }
}
