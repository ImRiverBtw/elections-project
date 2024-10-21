package com.election.electionbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
public class ElectionBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectionBackendApplication.class, args);
    }
}

@RestController
class ElectionResultController {

    @GetMapping("/electionresult/constituency/{constituency_id}")
    public Map<String, Integer> getElectionResults(@PathVariable String constituency_id) {
        ElectionResultParser parser = new ElectionResultParser();
        String filePath = "election-backend/src/main/resources/election-results/constituency/Telling_TK2023_kieskring_" + constituency_id + ".eml.xml";
        return parser.parseElectionResults(filePath);
    }
}