package com.election.electionbackend.services.electionresult;

import com.election.electionbackend.DTO.electionresult.AffiliationDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityPartyDto;
import com.election.electionbackend.DTO.electionresult.NewAggregatedSeatDto;
import com.election.electionbackend.models.electionresults.Municipality;
import com.election.electionbackend.repositories.electionresults.MunicipalityRepository;
import com.election.electionbackend.repositories.electionresults.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MunicipalityService {

    private final MunicipalityRepository municipalityRepository;
    private final VoteRepository voteRepository;

    /**
     * Retrieves the top party for each municipality.
     * @return List of MunicipalityPartyDto containing the top party details for each municipality.
     */
    public List<MunicipalityPartyDto> getMunicipalitiesWithTopParty() {
        List<Municipality> municipalities = municipalityRepository.findAll();
        return municipalities.stream()
                .map(municipality -> {
                    List<AffiliationDto> affiliations = voteRepository.findTopParties(municipality.getId());
                    AffiliationDto affiliation = affiliations.get(0);
                    return new MunicipalityPartyDto(municipality.getId(), municipality.getName(), affiliation);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all municipalities.
     * @return List of MunicipalityDto containing details of all municipalities.
     */
    public List<MunicipalityDto> getAll(){
        List<Municipality> municipalities=  municipalityRepository.findAll();
        return municipalities.stream()
                .map(municipality -> {
                    return new MunicipalityDto(municipality.getId(), municipality.getName());
                }).collect(Collectors.toList());
    }

}
