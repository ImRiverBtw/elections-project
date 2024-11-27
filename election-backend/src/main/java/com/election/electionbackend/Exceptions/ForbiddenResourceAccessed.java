package com.election.electionbackend.Exceptions;

public class ForbiddenResourceAccessed extends RuntimeException {

    public ForbiddenResourceAccessed(String message) {
        super(message);
    }

}