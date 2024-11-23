package com.election.electionbackend.repositories.electionresults;

import com.election.electionbackend.models.electionresults.MunicipalityElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MunicipalityElectionResultRepository extends JpaRepository<MunicipalityElectionResult, Long> {
    List<MunicipalityElectionResult> findByMunicipality(String municipality);
}
