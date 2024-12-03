package com.election.electionbackend;

import com.election.electionbackend.services.AuthenticationService;
import com.election.electionbackend.services.UserService;
import com.election.electionbackend.services.electionresult.ElectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;

/**
 * Main application class for the Election application.
 * This class is responsible for bootstrapping the Spring Boot application.
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@RequiredArgsConstructor
public class ElectionBackendApplication {

    private static final Logger logger = LoggerFactory.getLogger(ElectionBackendApplication.class);
    private final ElectionService electionService;
    private final AuthenticationService authenticationService;

    public static void main(String[] args) {
        SpringApplication.run(ElectionBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner processXmlFilesOnStartup() {
        return args -> {
            logger.info("Starting Election Backend");
            System.out.println("Processing XML files on startup...");
            electionService.processAllXmlFiles();
            System.out.println("Finished processing XML files... inserting dummy users...");
            authenticationService.insertDummyData();
            System.out.println("Done");
        };
    }
}