package com.election.electionbackend.Exceptions;

public class BadRequest extends RuntimeException {

    public BadRequest(String message) {
        super(message);
    }

}
