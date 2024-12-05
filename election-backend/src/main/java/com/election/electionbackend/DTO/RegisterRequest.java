package com.election.electionbackend.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

}
