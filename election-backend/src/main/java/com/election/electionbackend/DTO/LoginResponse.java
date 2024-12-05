package com.election.electionbackend.DTO;

import com.election.electionbackend.models.forum.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private User user;
    private String tokenString;
}
