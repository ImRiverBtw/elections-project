package com.election.electionbackend;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ElectionResultParser {

    public Map<String, Integer> parseElectionResults(String filePath) {
        Map<String, Integer> results = new HashMap<>();
        try {
            File xmlFile = new File(getClass().getClassLoader().getResource(filePath).getFile());
            if (!xmlFile.exists()) {
                return results;  // File not found
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setNamespaceAware(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList totalVotesList = doc.getElementsByTagName("TotalVotes");
            for (int i = 0; i < totalVotesList.getLength(); i++) {
                Node totalVotesNode = totalVotesList.item(i);
                if (totalVotesNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element totalVotesElement = (Element) totalVotesNode;
                    NodeList selectionList = totalVotesElement.getElementsByTagName("Selection");
                    for (int j = 0; j < selectionList.getLength(); j++) {
                        Node selectionNode = selectionList.item(j);
                        if (selectionNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element selectionElement = (Element) selectionNode;
                            String partyName = getTagValue("RegisteredName", selectionElement);
                            if (partyName == null) {
                                partyName = "Candidate " + getTagValue("CandidateIdentifier", selectionElement);
                            }
                            String validVotesStr = getTagValue("ValidVotes", selectionElement);
                            if (validVotesStr != null) {
                                int validVotes = Integer.parseInt(validVotesStr);
                                results.put(partyName, results.getOrDefault(partyName, 0) + validVotes);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            NodeList childNodes = nodeList.item(0).getChildNodes();
            if (childNodes.getLength() > 0) {
                Node node = childNodes.item(0);
                return node.getNodeValue();
            }
        }
        return null;
    }
}
