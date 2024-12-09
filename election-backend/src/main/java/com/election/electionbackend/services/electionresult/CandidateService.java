package com.election.electionbackend.services.electionresult;

import com.election.electionbackend.DTO.electionresult.CandidateDto;
import com.election.electionbackend.DTO.electionresult.NewAggregatedSeatDto;
import com.election.electionbackend.models.electionresults.Candidate;
import com.election.electionbackend.models.id.CandidateId;
import com.election.electionbackend.repositories.electionresults.CandidateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final VoteService voteService;

    public List<CandidateDto> getAll(){
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates.stream()
                .map(candidate -> {
                    CandidateId id = candidate.getId();
                    int votes = voteService.getTotalVotesForCandidate(id.getCandidateIdentifier(), id.getPartyIdentifier());
                    return new CandidateDto(id.getCandidateIdentifier(), id.getPartyIdentifier(), candidate.getName(), votes);
                })
                .collect(Collectors.toList());
    }

    public List<CandidateDto> getAllByParty(String partyId){
        List<Candidate> candidates = candidateRepository.findByParty_Id(partyId);
        return candidates.stream()
                .map(candidate -> {
                    CandidateId id = candidate.getId();
                    int votes = voteService.getTotalVotesForCandidate(id.getCandidateIdentifier(), id.getPartyIdentifier());
                    return new CandidateDto(id.getCandidateIdentifier(), id.getPartyIdentifier(), candidate.getName(), votes);
                })
                .sorted(Comparator.comparingInt(CandidateDto::getVotes).reversed())
                .collect(Collectors.toList());
    }

    public CandidateDto findById(String identifier, String partyId){
        CandidateId id = new CandidateId(identifier, partyId);
        Candidate foundCandidate = candidateRepository.findById(id).orElse(null);
        if (foundCandidate != null){
            return new CandidateDto(
                    foundCandidate.getId().getCandidateIdentifier(),
                    foundCandidate.getId().getPartyIdentifier(),
                    foundCandidate.getName(), 0);
            //TODO replace zero with the actual votecount or create a new DTO
        }
        return null;
    }
}
