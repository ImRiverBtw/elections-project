package com.election.electionbackend.repositories.electionresults;

public class AffiliationVoteDTO {
    private String affiliation;
    private Long votes;

    public AffiliationVoteDTO(String affiliation, Long votes) {
        this.affiliation = affiliation;
        this.votes = votes;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }
}
