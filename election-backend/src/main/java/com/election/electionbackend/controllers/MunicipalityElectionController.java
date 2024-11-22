package com.election.electionbackend.controllers;

import com.election.electionbackend.models.electionresults.MunicipalityElectionResult;
import com.election.electionbackend.jpa.electionresults.MunicipalityElectionResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/municipalities")
public class MunicipalityElectionController {

    @Autowired
    private MunicipalityElectionResultRepository repository;

    // Get all municipalities
    @GetMapping
    public List<String> getMunicipalities() {
        return repository.findAll()
                .stream()
                .map(MunicipalityElectionResult::getMunicipality)
                .distinct()
                .toList();
    }

    // Get election results for a specific municipality
    @GetMapping("/results/{municipality}")
    public List<MunicipalityElectionResult> getResultsByMunicipality(@PathVariable String municipality) {
        return repository.findByMunicipality(municipality);
    }

    @PostMapping("/dummy")
    public String addDummyData() {
        // Amsterdam
        repository.save(new MunicipalityElectionResult("Amsterdam", "Party A", 1000, 25.0));
        repository.save(new MunicipalityElectionResult("Amsterdam", "Party B", 1500, 37.5));
        repository.save(new MunicipalityElectionResult("Amsterdam", "Party C", 800, 20.0));
        repository.save(new MunicipalityElectionResult("Amsterdam", "Party D", 700, 17.5));
        repository.save(new MunicipalityElectionResult("Amsterdam", "Party E", 400, 10.0));
        repository.save(new MunicipalityElectionResult("Amsterdam", "Party F", 300, 7.5));
        repository.save(new MunicipalityElectionResult("Amsterdam", "Party G", 500, 12.5));

        // Rotterdam
        repository.save(new MunicipalityElectionResult("Rotterdam", "Party A", 1200, 30.0));
        repository.save(new MunicipalityElectionResult("Rotterdam", "Party B", 1800, 45.0));
        repository.save(new MunicipalityElectionResult("Rotterdam", "Party C", 900, 22.5));
        repository.save(new MunicipalityElectionResult("Rotterdam", "Party D", 100, 2.5));
        repository.save(new MunicipalityElectionResult("Rotterdam", "Party E", 600, 15.0));
        repository.save(new MunicipalityElectionResult("Rotterdam", "Party F", 400, 10.0));
        repository.save(new MunicipalityElectionResult("Rotterdam", "Party G", 500, 12.5));

        // Den Haag
        repository.save(new MunicipalityElectionResult("Den Haag", "Party A", 1400, 35.0));
        repository.save(new MunicipalityElectionResult("Den Haag", "Party B", 900, 22.5));
        repository.save(new MunicipalityElectionResult("Den Haag", "Party C", 1100, 27.5));
        repository.save(new MunicipalityElectionResult("Den Haag", "Party D", 600, 15.0));
        repository.save(new MunicipalityElectionResult("Den Haag", "Party E", 300, 7.5));
        repository.save(new MunicipalityElectionResult("Den Haag", "Party F", 200, 5.0));
        repository.save(new MunicipalityElectionResult("Den Haag", "Party G", 400, 10.0));

        // Utrecht
        repository.save(new MunicipalityElectionResult("Utrecht", "Party A", 1000, 25.0));
        repository.save(new MunicipalityElectionResult("Utrecht", "Party B", 1400, 35.0));
        repository.save(new MunicipalityElectionResult("Utrecht", "Party C", 800, 20.0));
        repository.save(new MunicipalityElectionResult("Utrecht", "Party D", 800, 20.0));
        repository.save(new MunicipalityElectionResult("Utrecht", "Party E", 500, 12.5));
        repository.save(new MunicipalityElectionResult("Utrecht", "Party F", 700, 17.5));
        repository.save(new MunicipalityElectionResult("Utrecht", "Party G", 600, 15.0));

        // Eindhoven
        repository.save(new MunicipalityElectionResult("Eindhoven", "Party A", 1500, 37.5));
        repository.save(new MunicipalityElectionResult("Eindhoven", "Party B", 1200, 30.0));
        repository.save(new MunicipalityElectionResult("Eindhoven", "Party C", 800, 20.0));
        repository.save(new MunicipalityElectionResult("Eindhoven", "Party D", 500, 12.5));
        repository.save(new MunicipalityElectionResult("Eindhoven", "Party E", 600, 15.0));
        repository.save(new MunicipalityElectionResult("Eindhoven", "Party F", 400, 10.0));
        repository.save(new MunicipalityElectionResult("Eindhoven", "Party G", 700, 17.5));

        // Tilburg
        repository.save(new MunicipalityElectionResult("Tilburg", "Party A", 1100, 27.5));
        repository.save(new MunicipalityElectionResult("Tilburg", "Party B", 1500, 37.5));
        repository.save(new MunicipalityElectionResult("Tilburg", "Party C", 800, 20.0));
        repository.save(new MunicipalityElectionResult("Tilburg", "Party D", 600, 15.0));
        repository.save(new MunicipalityElectionResult("Tilburg", "Party E", 500, 12.5));
        repository.save(new MunicipalityElectionResult("Tilburg", "Party F", 700, 17.5));
        repository.save(new MunicipalityElectionResult("Tilburg", "Party G", 400, 10.0));

        // Add more municipalities if needed
        repository.save(new MunicipalityElectionResult("Groningen", "Party A", 1000, 25.0));
        repository.save(new MunicipalityElectionResult("Groningen", "Party B", 1400, 35.0));
        repository.save(new MunicipalityElectionResult("Groningen", "Party C", 900, 22.5));
        repository.save(new MunicipalityElectionResult("Groningen", "Party D", 700, 17.5));
        repository.save(new MunicipalityElectionResult("Groningen", "Party E", 500, 12.5));
        repository.save(new MunicipalityElectionResult("Groningen", "Party F", 400, 10.0));

        return "A lot more dummy data added!";
    }



}
