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
     * Endpoint to get seat count for a specific party.
     * @param partyId ID of the party.
     * @return Number of seats allocated to the party.
     */
    @GetMapping("/votes/affiliation/{partyId}/seats")
    public int getSeatCountForParty(@PathVariable String partyId) {
        return electionService.getSeatCountForParty(partyId);
    }

    /**
     * Endpoint to get the top party for each municipality.
     * @return List of MunicipalityPartyDto containing the top party details for each municipality.
     */
    @GetMapping("/municipalities/top-party")
    public List<MunicipalityPartyDto> getMunicipalitiesWithTopParty() {
        return electionService.getMunicipalitiesWithTopParty();
    }

    /**
     * Endpoint to get all municipalities.
     * @return List of MunicipalityDto containing details of all municipalities.
     */
    @GetMapping("/municipalities")
    public List<MunicipalityDto> getAllMunicipalities() {
        return electionService.getAllMunicipalities();
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