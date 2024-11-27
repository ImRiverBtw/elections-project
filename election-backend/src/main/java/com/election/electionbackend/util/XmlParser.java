package com.election.electionbackend.util;

import com.election.electionbackend.models.electionresults.*;
import org.w3c.dom.*;
import org.springframework.stereotype.Component;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing XML content related to votes.
 */
@Component
public class XmlParser {

    /**
     * Parses the given XML content and extracts vote information.
     *
     * @param xmlContent the XML content to parse
     * @return a list of Vote entities extracted from the XML content
     */
    public List<Vote> parseVotes(String xmlContent) {
        List<Vote> votes = new ArrayList<>();
        try {
            // Parse the XML content into a Document object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Important for namespaces
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlContent.getBytes()));

            // Retrieve the municipality information
            Element authorityElement = (Element) document.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "AuthorityIdentifier").item(0);
            if (authorityElement == null) {
                throw new RuntimeException("Missing AuthorityIdentifier in XML");
            }
            Municipality municipality = new Municipality(
                    authorityElement.getAttribute("Id"),
                    authorityElement.getTextContent().trim()
            );

            // Process each ReportingUnitVotes element (polling stations)
            NodeList reportingUnits = document.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "ReportingUnitVotes");
            for (int i = 0; i < reportingUnits.getLength(); i++) {
                Element reportingUnitElement = (Element) reportingUnits.item(i);

                // Retrieve polling station information
                Element reportingUnitIdentifier = (Element) reportingUnitElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "ReportingUnitIdentifier").item(0);
                if (reportingUnitIdentifier == null) {
                    throw new RuntimeException("Missing ReportingUnitIdentifier in XML");
                }
                PollingStation pollingStation = new PollingStation(
                        reportingUnitIdentifier.getAttribute("Id"),
                        reportingUnitIdentifier.getTextContent().trim(),
                        municipality
                );

                // Process each Selection element (parties and candidates)
                NodeList selections = reportingUnitElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "Selection");
                Party currentParty = null;
                for (int j = 0; j < selections.getLength(); j++) {
                    Element selectionElement = (Element) selections.item(j);

                    // Check if the selection is for a party
                    NodeList affiliationNodes = selectionElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "AffiliationIdentifier");
                    if (affiliationNodes.getLength() > 0) {
                        Element affiliationElement = (Element) affiliationNodes.item(0);
                        currentParty = new Party(
                                affiliationElement.getAttribute("Id"),
                                affiliationElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "RegisteredName").item(0).getTextContent().trim()
                        );

                        // Add votes for the party
                        Node validVotesNode = selectionElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "ValidVotes").item(0);
                        int partyVotes = validVotesNode != null ? Integer.parseInt(validVotesNode.getTextContent().trim()) : 0;
                        votes.add(new Vote(null, pollingStation, currentParty, null, partyVotes));
                    }

                    // Process candidate information (if present)
                    NodeList candidateNodes = selectionElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "Candidate");
                    for (int k = 0; k < candidateNodes.getLength(); k++) {
                        Element candidateElement = (Element) candidateNodes.item(k);
                        Element candidateIdentifier = (Element) candidateElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "CandidateIdentifier").item(0);
                        Candidate candidate = new Candidate(
                                candidateIdentifier != null ? candidateIdentifier.getAttribute("Id") : "Unknown",
                                null, // Name not present in XML
                                currentParty
                        );

                        // Add votes for the candidate
                        Node candidateVotesNode = selectionElement.getElementsByTagNameNS("urn:oasis:names:tc:evs:schema:eml", "ValidVotes").item(0);
                        int candidateVotes = candidateVotesNode != null ? Integer.parseInt(candidateVotesNode.getTextContent().trim()) : 0;
                        votes.add(new Vote(null, pollingStation, currentParty, candidate, candidateVotes));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse XML", e);
        }

        return votes;
    }
}