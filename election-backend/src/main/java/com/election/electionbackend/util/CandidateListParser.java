package com.election.electionbackend.util;

import com.election.electionbackend.Exceptions.ResourceNotFound;
import com.election.electionbackend.models.electionresults.Candidate;
import com.election.electionbackend.models.electionresults.Party;
import com.election.electionbackend.models.id.CandidateId;
import com.election.electionbackend.repositories.electionresults.PartyRepository;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CandidateListParser {

    private static final String NAMESPACE = "urn:oasis:names:tc:evs:schema:eml";
    private static final String  NAMESPACE_NL = "urn:oasis:names:tc:ciq:xsdschema:xNL:2.0";
    private final PartyRepository partyRepository;

    public CandidateListParser(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }


    public List<Candidate> parse(String xmlContent){
        List<Candidate> candidateList = new ArrayList<>();
        Document document = buildDocument(xmlContent);

        NodeList affiliationNodeList = document.getElementsByTagNameNS(NAMESPACE, "Affiliation");

        for (int i = 0; i < affiliationNodeList.getLength(); i++) {
            Element affiliationElement = (Element) affiliationNodeList.item(i);
            String affiliationId = getAffiliationId(affiliationElement);
            Party currentParty = partyRepository.findById(affiliationId)
                    .orElseThrow(() -> new ResourceNotFound("Party not found"));

            NodeList candidateNodeList = affiliationElement.getElementsByTagNameNS(NAMESPACE, "Candidate");
            for (int j = 0; j < candidateNodeList.getLength(); j++) {
                Element candidateElement = (Element) candidateNodeList.item(j);
                String candidateIdentifier = getCandidateId(candidateElement);
                String fullName = getFullName(candidateElement);
                CandidateId candidateIdObj = new CandidateId(candidateIdentifier, affiliationId);
                Candidate candidate = new Candidate(candidateIdObj, fullName, currentParty);
                candidateList.add(candidate);
            }
        }
        return candidateList;
    }

    private Document buildDocument(String xmlContent){
        try {
            // Parse the XML content into a Document object
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Important for namespaces
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(xmlContent.getBytes()));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Error configuring the XML parser", e);
        } catch (SAXException e) {
            throw new RuntimeException("Error Parsing XML content",e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAffiliationId(Element affiliationElement) {
        Element affiliationIdentifier = (Element) affiliationElement.getElementsByTagNameNS(NAMESPACE, "AffiliationIdentifier").item(0);
        if (affiliationIdentifier == null) {
            throw new RuntimeException("no affiliation identifier found");
        }
        return affiliationIdentifier.getAttribute("Id");
    }

    private String getCandidateId(Element candidateElement) {
        Element candidateIdentifier = (Element) candidateElement.getElementsByTagNameNS(NAMESPACE, "CandidateIdentifier").item(0);
        if (candidateIdentifier == null) {
            throw new RuntimeException("no candidate identifier found");
        }
        return candidateIdentifier.getAttribute("Id");
    }

    private String getFullName(Element candidateElement) {
        Element fullNameElement = (Element) candidateElement.getElementsByTagNameNS(NAMESPACE, "CandidateFullName").item(0);
        if (fullNameElement == null) {
            throw new RuntimeException("no full name found");
        }

        Element personNameElement = (Element) fullNameElement.getElementsByTagNameNS(NAMESPACE_NL, "PersonName").item(0);
        if (personNameElement == null) {
            throw new RuntimeException("no person name found");
        }

        String firstName = getTextContentByTagName(personNameElement, "FirstName");
        String namePrefix = getTextContentByTagName(personNameElement, "NamePrefix");
        String lastName = getTextContentByTagName(personNameElement, "LastName");

        String fullNameRaw = firstName + " " + namePrefix + " " + lastName;
        return fullNameRaw.replaceAll(" {2}", " ");
    }

    private String getTextContentByTagName(Element parent, String tagName) {
        Element textElement = (Element) parent.getElementsByTagNameNS(NAMESPACE_NL, tagName).item(0);
        if (textElement == null) {
            return "";
        }
        return textElement.getTextContent().trim();
    }
}
