package com.election.electionbackend.models.forum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;


public enum UserRole {
    USER_ROLE,
    ADMIN_ROLE;
}