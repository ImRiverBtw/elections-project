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