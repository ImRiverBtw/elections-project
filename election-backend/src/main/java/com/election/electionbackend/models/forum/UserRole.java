package com.election.electionbackend.models.forum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity
public class UserRole implements GrantedAuthority {

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String ROLE_ADMIN_NAME = "ADMIN";
    public static final String ROLE_USER_NAME = "USER";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The name of the role (also called GrantedAuthority).
     */
    private String authority;

    public UserRole(String authority) {
        this.authority = authority;
    }

    public UserRole() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole userRole)) return false;

        return getAuthority() != null
                ? getAuthority().equals(userRole.getAuthority())
                : userRole.getAuthority() == null;
    }

    @Override
    public int hashCode() {
        return getAuthority() != null ? getAuthority().hashCode() : 0;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}