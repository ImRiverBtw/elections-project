package com.election.electionbackend.services.electionresult;


import com.election.electionbackend.DTO.electionresult.NewAggregatedSeatDto;
import com.election.electionbackend.DTO.electionresult.NewAggregatedVoteDto;
import com.election.electionbackend.repositories.electionresults.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteService {

    @Autowired
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
     * Retrieves the total seats for each party based on the D'Hondt method and electoral quota.
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
