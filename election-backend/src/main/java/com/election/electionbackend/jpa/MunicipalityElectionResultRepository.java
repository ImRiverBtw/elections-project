package com.election.electionbackend.jpa;

import com.election.electionbackend.entity.MunicipalityElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MunicipalityElectionResultRepository extends JpaRepository<MunicipalityElectionResult, Long> {
    List<MunicipalityElectionResult> findByMunicipality(String municipality);
}
