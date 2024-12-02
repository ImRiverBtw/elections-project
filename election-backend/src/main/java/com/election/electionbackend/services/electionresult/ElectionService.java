package com.election.electionbackend.services.electionresult;

import com.election.electionbackend.DTO.electionresult.*;
import com.election.electionbackend.models.electionresults.*;
import com.election.electionbackend.repositories.electionresults.*;
import com.election.electionbackend.util.XmlParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectionService {

    private static final String DIRECTORY = "election-backend/src/main/resources/xml/municipality/";
    private static final String FILE_PATTERN = "Telling_TK2023_gemeente_.*\\.eml\\.xml";

    private final MunicipalityRepository municipalityRepository;
    private final PollingStationRepository pollingStationRepository;
    private final PartyRepository partyRepository;
    private final CandidateRepository candidateRepository;
    private final VoteRepository voteRepository;
    private final XmlParser xmlParser;



    /**
     * Retrieves the total votes by party in a specific municipality.
     * @param municipalityId ID of the municipality.
     * @return List of AggregatedVoteDto containing vote details for each party in the municipality.
     */
    public List<AggregatedVoteDto> getTotalVotesByPartyInMunicipality(String municipalityId) {
        List<Vote> votes = voteRepository.findByPollingStation_Municipality_Id(municipalityId);
        int totalValidVotes = votes.stream().mapToInt(Vote::getValidVotes).sum();
        int electoralQuota = totalValidVotes / 150;

        return votes.stream()
                .filter(vote -> vote.getParty() != null)
                .collect(Collectors.groupingBy(
                        vote -> vote.getParty().getId(),
                        Collectors.summingInt(Vote::getValidVotes)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Party party = partyRepository.findById(entry.getKey()).orElseThrow();
                    int totalVotes = entry.getValue();
                    int seatCount = totalVotes / electoralQuota;
                    double votePercentage = (double) totalVotes / totalValidVotes * 100;
                    return new AggregatedVoteDto(Integer.parseInt(party.getId()), party.getName(), totalVotes, seatCount, votePercentage);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the total votes by party in a specific polling station.
     * @param pollingStationId ID of the polling station.
     * @return List of AggregatedVoteDto containing vote details for each party in the polling station.
     */
    public List<AggregatedVoteDto> getTotalVotesByPartyInPollingStation(String pollingStationId) {
        List<Vote> allVotes = voteRepository.findAll();
        int totalValidVotes = allVotes.stream().mapToInt(Vote::getValidVotes).sum();
        int electoralQuota = totalValidVotes / 150;

        return voteRepository.findByPollingStation_Id(pollingStationId).stream()
                .filter(vote -> vote.getParty() != null)
                .collect(Collectors.groupingBy(
                        vote -> vote.getParty().getId(),
                        Collectors.summingInt(Vote::getValidVotes)
                ))
                .entrySet().stream()
                .map(entry -> {
                    Party party = partyRepository.findById(entry.getKey()).orElseThrow();
                    int totalVotes = entry.getValue();
                    int seatCount = totalVotes / electoralQuota;
                    double votePercentage = (double) totalVotes / totalValidVotes * 100;
                    return new AggregatedVoteDto(Integer.parseInt(party.getId()), party.getName(), totalVotes, seatCount, votePercentage);
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the total number of valid votes for a specific party.
     * @param partyId ID of the party.
     * @return Total number of valid votes for the party.
     */
    public int getTotalVotesForParty(String partyId) {
        return voteRepository.findByParty_Id(partyId).stream()
                .mapToInt(Vote::getValidVotes)
                .sum();
    }

    /**
     * Retrieves the total number of valid votes for a specific candidate.
     * @param candidateId ID of the candidate.
     * @return Total number of valid votes for the candidate.
     */
    public int getTotalVotesForCandidate(String candidateId) {
        return voteRepository.findByCandidate_Id(candidateId).stream()
                .mapToInt(Vote::getValidVotes)
                .sum();
    }

    /**
     * Retrieves the details of a specific municipality by its ID.
     * @param municipalityId ID of the municipality.
     * @return MunicipalityDto containing details of the municipality.
     */
    public MunicipalityDto getMunicipalityById(String municipalityId) {
        Municipality municipality = municipalityRepository.findById(municipalityId)
                .orElseThrow(() -> new NoSuchElementException("Municipality not found"));
        MunicipalityDto dto = new MunicipalityDto();
        dto.setId(municipality.getId());
        dto.setName(municipality.getName());
        return dto;
    }

    /**
     * Retrieves the polling stations for a specific municipality.
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

    /**
     * Processes all XML files in the specified directory.
     * Reads the XML files, parses the data, and saves the votes to the database.
     */
    public void processAllXmlFiles() {
        try {
            List<Path> xmlFiles = Files.walk(Paths.get(DIRECTORY))
                    .filter(path -> path.getFileName().toString().matches(FILE_PATTERN))
                    .toList();

            for (Path file : xmlFiles) {
                System.out.println("Processing file: " + file);
                String xmlContent = Files.readString(file);
                processXmlData(xmlContent);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to process XML files in directory: " + DIRECTORY, e);
        }
    }

    /**
     * Processes the XML data and saves the votes to the database.
     * @param xmlContent XML content as a string.
     */
    public void processXmlData(String xmlContent) {
        List<Vote> votes = xmlParser.parseVotes(xmlContent);

        for (Vote vote : votes) {
            Municipality municipality = municipalityRepository.save(vote.getPollingStation().getMunicipality());
            vote.getPollingStation().setMunicipality(municipality);

            PollingStation pollingStation = pollingStationRepository.save(vote.getPollingStation());
            vote.setPollingStation(pollingStation);

            if (vote.getParty() != null) {
                Party party = partyRepository.save(vote.getParty());
                vote.setParty(party);
            }

            if (vote.getCandidate() != null) {
                Candidate candidate = candidateRepository.save(vote.getCandidate());
                vote.setCandidate(candidate);
            }

            voteRepository.save(vote);
        }
    }
}