package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.DTO.electionresult.AggregatedVoteDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityPartyDto;
import com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto;
import com.election.electionbackend.services.electionresult.MunicipalityService;
import com.election.electionbackend.services.electionresult.VoteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/electionresult/municipalities")
public class MunicipalityController {

    private final MunicipalityService municipalityService;
    private final VoteService voteService;

    public MunicipalityController(MunicipalityService municipalityService, VoteService voteService) {
        this.municipalityService = municipalityService;
        this.voteService = voteService;
    }

    /**
     * Endpoint to get all municipalities.
     * @return List of MunicipalityDto containing details of all municipalities.
     */
    @GetMapping("")
    public List<MunicipalityDto> getAllMunicipalities() {
        return municipalityService.getAll();
    }

    /**
     * Endpoint to get the top party for each municipality.
     * @return List of MunicipalityPartyDto containing the top party details for each municipality.
     */
    @GetMapping("/top-party")
    public List<MunicipalityPartyDto> getMunicipalitiesWithTopParty() {
        return municipalityService.getMunicipalitiesWithTopParty();
    }

    /**
     * Endpoint to get total votes by party in a specific municipality.
     * @param municipalityId ID of the municipality.
     * @return List of NewAggregatedVoteDto containing vote details for each party in the municipality.
     */
    @GetMapping("{municipalityId}/votes")
    public List<NewAggregatedVoteDto> getVotesByPartyInMunicipality(@PathVariable String municipalityId) {
        return voteService.getTotalVotesByPartyForMunicipality(municipalityId);
    }
}
