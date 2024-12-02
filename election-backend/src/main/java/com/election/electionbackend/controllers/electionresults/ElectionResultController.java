package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.DTO.electionresult.AggregatedVoteDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityPartyDto;
import com.election.electionbackend.services.electionresult.ElectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electionresult")
public class ElectionResultController {

    private final ElectionService electionService;

    // Constructor injection for ElectionService
    public ElectionResultController(ElectionService electionService) {
        this.electionService = electionService;
    }


    /**
     * Endpoint to get total votes by party in a specific municipality.
     * @param municipalityId ID of the municipality.
     * @return List of AggregatedVoteDto containing vote details for each party in the municipality.
     */
    @GetMapping("/municipalities/{municipalityId}/votes")
    public List<AggregatedVoteDto> getVotesByPartyInMunicipality(@PathVariable String municipalityId) {
        return electionService.getTotalVotesByPartyInMunicipality(municipalityId);
    }
}