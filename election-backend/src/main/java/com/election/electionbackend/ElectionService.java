package com.election.electionbackend;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ElectionService {

    public Map<String, Integer> getElectionResults(String constituencyId) {
        ElectionResultParser parser = new ElectionResultParser();
        String filePath = "election-results/constituency/Telling_TK2023_kieskring_" + constituencyId + ".eml.xml";
        return parser.parseElectionResults(filePath);
    }
}
