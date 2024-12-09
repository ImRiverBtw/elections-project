package com.election.electionbackend.services.electionresult;

import com.election.electionbackend.DTO.electionresult.CandidateDto;
import com.election.electionbackend.DTO.electionresult.NewAggregatedSeatDto;
import com.election.electionbackend.models.electionresults.Candidate;
import com.election.electionbackend.models.id.CandidateId;
import com.election.electionbackend.repositories.electionresults.CandidateRepository;
import com.election.electionbackend.util.CandidateListParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final VoteService voteService;

    private static final String DIRECTORY = "election-backend/src/main/resources/xml/candidate/";
    private static final String FILE_PATTERN = "Kandidatenlijsten_TK2023_.*\\.eml\\.xml";
    private final CandidateListParser candidateListParser;

    public List<Path> findXmlFiles() {
        List<Path> xmlFiles = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(Paths.get(DIRECTORY))) {
            xmlFiles = stream
                    .filter(path -> path.getFileName().toString().matches(FILE_PATTERN))
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to find XML files in directory: " + DIRECTORY, e);
        }
        return xmlFiles;
    }

    public void processXmlFiles() {
        List<Path> xmlFiles = findXmlFiles();
        for (Path file : xmlFiles) {
            try {
                System.out.println("Processing file: " + file);
                String xmlContent = Files.readString(file);
                processXmlData(xmlContent);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read contents of file: " + file, e);
            }
        }
    }

    public void processXmlData(String xmlContent) {
        List<Candidate> candidates = candidateListParser.parse(xmlContent);
        candidateRepository.saveAll(candidates);
    }


    public List<CandidateDto> getAll() {
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates.stream()
                .map(candidate -> {
                    CandidateId id = candidate.getId();
                    int votes = voteService.getTotalVotesForCandidate(id.getCandidateIdentifier(), id.getPartyIdentifier());
                    return new CandidateDto(id.getCandidateIdentifier(), id.getPartyIdentifier(), candidate.getName(), votes);
                })
                .collect(Collectors.toList());
    }

    public List<CandidateDto> getAllByParty(String partyId) {
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

    public CandidateDto findById(String identifier, String partyId) {
        CandidateId id = new CandidateId(identifier, partyId);
        Candidate foundCandidate = candidateRepository.findById(id).orElse(null);
        if (foundCandidate != null) {
            return new CandidateDto(
                    foundCandidate.getId().getCandidateIdentifier(),
                    foundCandidate.getId().getPartyIdentifier(),
                    foundCandidate.getName(), 0);
            //TODO replace zero with the actual votecount or create a new DTO
        }
        return null;
    }
}
