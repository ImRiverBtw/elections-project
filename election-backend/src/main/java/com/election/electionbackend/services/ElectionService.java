package com.election.electionbackend.services;

import com.election.electionbackend.DTO.electionresult.AggregatedVoteDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityDto;
import com.election.electionbackend.DTO.electionresult.MunicipalityPartyDto;
import com.election.electionbackend.DTO.electionresult.PollingStationDto;
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

    public List<AggregatedVoteDto> getTotalVotesByParty() {
        List<Vote> allVotes = voteRepository.findAll();
        int totalValidVotes = allVotes.stream().mapToInt(Vote::getValidVotes).sum();
        int electoralQuota = totalValidVotes / 150;

        return allVotes.stream()
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
                    double votePercentage = Math.round((double) totalVotes / totalValidVotes * 10000) / 100.0;
                    return new AggregatedVoteDto(Integer.parseInt(party.getId()), party.getName(), totalVotes, seatCount, votePercentage);
                })
                .collect(Collectors.toList());
    }

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

    public int getTotalVotesForParty(String partyId) {
        return voteRepository.findByParty_Id(partyId).stream()
                .mapToInt(Vote::getValidVotes)
                .sum();
    }

    public int getTotalVotesForCandidate(String candidateId) {
        return voteRepository.findByCandidate_Id(candidateId).stream()
                .mapToInt(Vote::getValidVotes)
                .sum();
    }

    public MunicipalityDto getMunicipalityById(String municipalityId) {
        Municipality municipality = municipalityRepository.findById(municipalityId)
                .orElseThrow(() -> new NoSuchElementException("Municipality not found"));
        MunicipalityDto dto = new MunicipalityDto();
        dto.setId(municipality.getId());
        dto.setName(municipality.getName());
        return dto;
    }

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

    public List<MunicipalityPartyDto> getMunicipalitiesWithTopParty() {
        List<Municipality> municipalities = municipalityRepository.findAll();
        List<MunicipalityPartyDto> result = new ArrayList<>();

        for (Municipality municipality : municipalities) {
            List<Vote> votes = voteRepository.findByPollingStation_Municipality_Id(municipality.getId());
            Map<String, Integer> partyVotes = votes.stream()
                    .filter(vote -> vote.getParty() != null)
                    .collect(Collectors.groupingBy(
                            vote -> vote.getParty().getId(),
                            Collectors.summingInt(Vote::getValidVotes)
                    ));

            if (!partyVotes.isEmpty()) {
                Map.Entry<String, Integer> topPartyEntry = partyVotes.entrySet().stream()
                        .max(Map.Entry.comparingByValue())
                        .orElseThrow();

                Party topParty = partyRepository.findById(topPartyEntry.getKey()).orElseThrow();
                MunicipalityPartyDto.Affiliation affiliation = new MunicipalityPartyDto.Affiliation(
                        topParty.getId(), topParty.getName(), topPartyEntry.getValue()
                );
                result.add(new MunicipalityPartyDto(municipality.getId(), municipality.getName(), affiliation));
            }
        }

        return result;
    }

    public int getSeatCountForParty(String partyId) {
        List<Vote> allVotes = voteRepository.findAll();
        int totalValidVotes = allVotes.stream().mapToInt(Vote::getValidVotes).sum();
        int electoralQuota = totalValidVotes / 150;

        int totalVotesForParty = voteRepository.findByParty_Id(partyId).stream()
                .mapToInt(Vote::getValidVotes)
                .sum();

        return totalVotesForParty / electoralQuota;
    }

    public List<MunicipalityDto> getAllMunicipalities() {
        List<Municipality> municipalities = municipalityRepository.findAll();
        return municipalities.stream()
                .map(municipality -> {
                    MunicipalityDto dto = new MunicipalityDto();
                    dto.setId(municipality.getId());
                    dto.setName(municipality.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}