package com.election.electionbackend.services.electionresult;

import com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto;
import com.election.electionbackend.DTO.electionresult.PollingStationDto;
import com.election.electionbackend.repositories.electionresults.PollingStationRepository;
import com.election.electionbackend.repositories.electionresults.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollingStationService {

    private final PollingStationRepository pollingStationRepository;
    private final VoteRepository voteRepository;

    /**
     * Retrieves the amount of votes each party got in a specific polling station
     *
     * @param pollingStationId The Id of the polling station.
     * @return List of newAggregatedVoteDto containing the total amount of votes each party got in the polling station
     */
    public List<NewAggregatedVoteDto> getTotalVotesByPartyInPollingStation(String pollingStationId) {
        long totalVotes = voteRepository.getTotalValidVotesForPollingStation(pollingStationId);
        return voteRepository.findVotesGroupedByPartyForPollingStation(totalVotes, pollingStationId);
    }

    /**
     * Retrieves the polling stations for a specific municipality.
     *
     * @param municipalityId ID of the municipality.
     * @return List of PollingStationDto containing details of the polling stations.
     */
    public List<PollingStationDto> getPollingStationsByMunicipality(String municipalityId) {
        return pollingStationRepository.findByMunicipality_Id(municipalityId).stream()
                .map(pollingStation -> {
                    PollingStationDto dto = new PollingStationDto();
                    dto.setId(pollingStation.getId());
                    dto.setName(pollingStation.getName());
                    dto.setMunicipalityId(pollingStation.getMunicipality().getId());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
