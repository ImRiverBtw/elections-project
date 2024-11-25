package com.election.electionbackend.models.forum;


import com.election.electionbackend.security.SecureHasher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account implements UserDetails {


    @GeneratedValue
    @Id
    private Long userID;
    private String displayName;
    private String email;
    @JsonIgnore
    private String hashedPassword = null;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public String hashPassword(String password){
        return SecureHasher.secureHash("Id-" + this.getUserID() + ":" + password);
    }

    public void setPassword(String newPassword) {
        this.setHashedPassword(hashPassword(newPassword));
    }

    public String getPassword(){
        return this.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public boolean verifyPassword(String password){
        return this.hashPassword(password).equals(this.getHashedPassword());
    }

    public Account createSample(long id, String displayName){
        Account newAccount = new Account(
                id,
                displayName,
                displayName + "@hva.nl",
                hashPassword("Welkom1@"),
                UserRole.USER_ROLE);
        return newAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public String toString() {
        return String.format("{ login=%s, callName=%s, id=%d }", this.email, this.displayName, this.userID);
    }
}
