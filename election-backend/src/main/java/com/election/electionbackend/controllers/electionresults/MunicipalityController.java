package com.election.electionbackend.controllers.electionresults;

import com.election.electionbackend.DTO.electionresult.MunicipalityDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityPartyDto;
import com.election.electionbackend.services.electionresult.MunicipalityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/electionresult/municipalities")
public class MunicipalityController {

    private final MunicipalityService municipalityService;

    public MunicipalityController(MunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }

    /**
     * Endpoint to get all municipalities.
     * @return List of MunicipalityDto containing details of all municipalities.
     */
    @GetMapping("")
    public List<MunicipalityDto> getAllMunicipalities(){
        return municipalityService.getAll();
    }

    /**
     * Endpoint to get the top party for each municipality.
     * @return List of MunicipalityPartyDto containing the top party details for each municipality.
     */
    @GetMapping("/top-party")
    public List<MunicipalityPartyDto> getMunicipalitiesWithTopParty(){
        return municipalityService.getMunicipalitiesWithTopParty();
    }

}
