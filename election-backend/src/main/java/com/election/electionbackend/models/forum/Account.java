package com.election.electionbackend.models.forum;


import com.election.electionbackend.config.security.beans.SecureHasher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import static jakarta.persistence.FetchType.EAGER;

@Entity
public class Account implements UserDetails {

    @GeneratedValue
    @Id
    private UUID id;

    private String userName;
    private String email;

    @JsonIgnore
    private String hashedPassword = null;


    @ManyToMany(fetch = EAGER)
    private HashSet<UserRole> authorities = new HashSet<>();

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    public Account() {}
    public Account(String name, String email) {
        this.userName = name;
        this.email = email;
    }

    public String hashPassword(String password) throws InterruptedException {
        return SecureHasher.secureHash("Id-" + this.getId() + ":" + password, 0);
    }
    public void setPassword(String password) throws InterruptedException {
        this.setHashedPassword(hashPassword(password));
    }


    @Override
    public HashSet<UserRole> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(HashSet<UserRole> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }
    public void setUserName(String name) {
        this.userName = name;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return getId() == account.getId();
    }

    public void addRole(UserRole role) {
        if (getAuthorities() == null || !(getAuthorities() instanceof HashSet))
            setAuthorities(new HashSet<>(Objects.requireNonNullElse(getAuthorities(), new HashSet<>())));

        getAuthorities().add(role);
    }


}
