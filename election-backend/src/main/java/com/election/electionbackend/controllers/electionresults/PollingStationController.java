package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto;
import com.election.electionbackend.services.electionresult.PollingStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/electionresult/pollingstation")
public class PollingStationController {


    private final PollingStationService pollingStationService;

    public PollingStationController(PollingStationService pollingStationService) {
        this.pollingStationService = pollingStationService;
    }

    @GetMapping("/{pollingstation_id}/votes")
    public List<NewAggregatedVoteDto> getVotesByParty(@PathVariable String pollingstation_id) {
        return pollingStationService.getTotalVotesByPartyInPollingStation(pollingstation_id);
    }
}
