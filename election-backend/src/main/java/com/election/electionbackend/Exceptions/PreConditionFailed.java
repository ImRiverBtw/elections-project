package com.election.electionbackend.Exceptions;

public class PreConditionFailed extends RuntimeException {

    public PreConditionFailed(String message) {
        super(message);
    }

}