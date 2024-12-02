package com.election.electionbackend.services.electionresult;


import com.election.electionbackend.DTO.electionresult.NewAggregatedSeatDto;
import com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto;
import com.election.electionbackend.repositories.electionresults.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;

    /**
     * Retrieves the total votes grouped by party and calculates vote percentages.
     * @return A list of NewAggregatedVoteDto containing party ID, name, total votes, and vote percentage.
     */
    public List<NewAggregatedVoteDto> getTotalVotesByParty() {
        long totalValidVotes = voteRepository.getTotalValidVotes();
        return voteRepository.findVotesGroupedByParty(totalValidVotes);
    }

    /**
     * Retrieves the total votes by party in a specific municipality.
     * @param municipalityId ID of the municipality.
     * @return List of NewAggregatedVoteDto containing vote details for each party in the municipality.
     */
    public List<NewAggregatedVoteDto> getTotalVotesByPartyForMunicipality(String municipalityId) {
        long totalValidVotes = voteRepository.getTotalValidVotesForMunicipality(municipalityId);
        return voteRepository.findVotesGroupedByPartyForMunicipality(totalValidVotes, municipalityId);
    }

    /**
     * Retrieves the total seats for each party
     * @return A list of NewAggregatedSeatDto containing party ID, name, and the number of seats allocated.
     */
    public List<NewAggregatedSeatDto> getTotalSeatsByParty() {
     List<NewAggregatedVoteDto> voteList = getTotalVotesByParty();

     // Map the list of votes to a list of seats, calculating the number of seats per party
     return voteList.stream()
             .map(voteDto -> {
                 int seatCount = calculateSeats((int) voteDto.getVotes());
                 return new NewAggregatedSeatDto(voteDto.getId(), voteDto.getName(), seatCount);
             })
             .collect(Collectors.toList());
    }

    /**
     * Retrieves the total number of valid votes for a specific party
     * @param partyId ID of the party
     * @return Total number of valid votes for the party
     */
    public int getTotalVotesForParty(String partyId){
        return voteRepository.findTotalVotesForParty(partyId);
    };


    /**
     * Retrieves the total number of valid votes for a specific candidate.
     * @param candidateId ID of the candidate.
     * @return Total number of valid votes for the candidate.
     */
    public int getTotalVotesForCandidate(String candidateId){return voteRepository.findTotalVotesForCandidate(candidateId);};

    /**
     * Retrieves the total number of seats for a specific party
     * @param partyId ID of the party
     * @return Total number of seats for the party
     */
   public int getTotalSeatsForParty(String partyId){
       int votes = getTotalVotesForParty(partyId);
       return calculateSeats(votes);
   }

    /**
     * Calculates the number of seats a party has based on its total votes
     * @param votes The total votes received by the party.
     * @return The number of seats the party is allocated based on its vote count.
     */
    private int calculateSeats(int votes){
        long totalValidVotes = voteRepository.getTotalValidVotes();
        int ectoralQuota = (int)totalValidVotes / 150;
        return votes / ectoralQuota;
    }

}
